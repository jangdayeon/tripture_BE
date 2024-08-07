package com.photoChallenger.tripture.domain.comment.dto;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindNestedComment {
    Long profileId;
    String nickname;
    String commentCalculatedDate;
    String commentContent;

    public static FindNestedComment from(Comment comment) {
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

        return new FindNestedComment(comment.getProfileId(),
                comment.getPost().getProfile().getProfileNickname(),
                caculateResult,
                comment.getCommentContent());
    }
}
