package com.photoChallenger.tripture.domain.comment.dto;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.photoChallenger.tripture.global.S3.S3Url.S3_URL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindComment {
    Long CommentId;
    Long profileId;
    String profileImgName;
    String nickname;
    String commentCalculatedDate;
    String commentContent;
    Boolean blockChk;

    public static FindComment from(CommentDto comment, boolean isBlocked) {
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

        return new FindComment(comment.getCommentId(),comment.getProfileId(),
                S3_URL + comment.getProfileImgName(),
                comment.getProfileNickname(),
                caculateResult,
                comment.getCommentContent(),
                isBlocked);
    }
}
