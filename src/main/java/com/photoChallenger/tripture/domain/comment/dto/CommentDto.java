package com.photoChallenger.tripture.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    Long CommentId;
    Long profileId;
    String profileImgName;
    String profileNickname;
    LocalDateTime commentDate;
    String commentContent;
}
