package com.photoChallenger.tripture.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WriteCommentRequest {
    Long groupId;
    Long postId;
    String commentContent;
}
