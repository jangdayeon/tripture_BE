package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.FindAllNestedComment;
import com.photoChallenger.tripture.domain.comment.dto.MyCommentListResponse;
import com.photoChallenger.tripture.domain.comment.dto.FindAllNotNestedComment;
import com.photoChallenger.tripture.domain.comment.dto.WriteCommentRequest;

import java.util.List;

public interface CommentService {
    MyCommentListResponse findMyComments(Long loginId, int pageNo);

    Long writeComment(WriteCommentRequest writeCommentRequest, Long loginId);

    FindAllNestedComment findAllNestedComment(Long postId, Long loginId);

    void deleteComment(Long commentId);

    FindAllNotNestedComment findAllNotNestedComment(Long postId, int pageNo, Long loginId);
}
