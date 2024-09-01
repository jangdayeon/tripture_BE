package com.photoChallenger.tripture.domain.postLike.service;

import com.photoChallenger.tripture.domain.postLike.dto.LikeSaveResponse;

public interface PostLikeService {
    LikeSaveResponse postLikeAdd(Long postId, Long loginId);
}
