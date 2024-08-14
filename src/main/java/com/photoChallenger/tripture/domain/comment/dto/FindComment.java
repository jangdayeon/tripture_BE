package com.photoChallenger.tripture.domain.comment.dto;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindComment {
    Long profileId;
    String profileImgName;
    String nickname;
    String commentCalculatedDate;
    String commentContent;
    Boolean blockChk;

    public static FindComment from(Comment comment, boolean isBlocked) {
        Duration duration = Duration.between(comment.getCommentDate(), LocalDateTime.now());

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        long remainingHours = hours % 24;
        long remainingMinutes = minutes % 60;

        String caculateResult = null;
        if (remainingHours == 0){
            caculateResult = String.format("%d분 전 작성", remainingMinutes);
        }
        else if(days == 0){
            caculateResult = String.format("%d시간 전 작성", remainingHours);
        }
        else{
            caculateResult = String.format("%d일 전 작성", days);
        }

        return new FindComment(comment.getProfileId(),
                comment.getPost().getProfile().getProfileImgName(),
                comment.getPost().getProfile().getProfileNickname(),
                caculateResult,
                comment.getCommentContent(),
                isBlocked);
    }
}
