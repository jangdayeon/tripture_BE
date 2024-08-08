package com.photoChallenger.tripture.domain.post.service;

import com.photoChallenger.tripture.domain.post.dto.MyPostResponse;
import com.photoChallenger.tripture.domain.post.dto.GetPostResponse;

import java.util.List;

public interface PostService {
    List<MyPostResponse> findMyPosts(Long loginId, int pageNo);

     GetPostResponse getPost(Long postId, Long loginId);
}
