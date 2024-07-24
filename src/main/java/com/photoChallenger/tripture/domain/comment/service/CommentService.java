package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.MyCommentResponse;

import java.util.List;

public interface CommentService {
    public List<MyCommentResponse> findMyComments(Long loginId);
}
