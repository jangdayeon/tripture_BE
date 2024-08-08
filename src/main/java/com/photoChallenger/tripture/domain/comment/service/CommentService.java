package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.MyCommentResponse;
import com.photoChallenger.tripture.domain.comment.dto.FindNestedAllComment;
import com.photoChallenger.tripture.domain.comment.dto.WriteCommentRequest;

import java.util.List;

public interface CommentService {
    List<MyCommentResponse> findMyComments(Long loginId, int pageNo);

    Long writeComment(WriteCommentRequest writeCommentRequest, Long loginId);

    FindNestedAllComment findAllNestedComment(Long postId);

    void deleteComment(Long commentId);
}
