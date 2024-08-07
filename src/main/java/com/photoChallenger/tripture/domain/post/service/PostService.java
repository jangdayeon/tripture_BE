package com.photoChallenger.tripture.domain.post.service;

import com.photoChallenger.tripture.domain.post.dto.MyPostResponse;

import java.util.List;

public interface PostService {
    public List<MyPostResponse> findMyPosts(Long loginId, int pageNo);
}
