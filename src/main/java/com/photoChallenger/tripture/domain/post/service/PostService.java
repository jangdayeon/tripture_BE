package com.photoChallenger.tripture.domain.post.service;

import com.photoChallenger.tripture.domain.post.dto.MyPostResponse;
import com.photoChallenger.tripture.domain.post.dto.GetPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<MyPostResponse> findMyPosts(Long loginId, int pageNo);

     GetPostResponse getPost(Long postId, Long loginId);

     void editPost(Long postId, MultipartFile file, String postContent) throws IOException;
}
