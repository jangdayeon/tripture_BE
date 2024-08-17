package com.photoChallenger.tripture.domain.post.service;

import com.photoChallenger.tripture.domain.post.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    MyPostListResponse findMyPosts(Long loginId, int pageNo);

     GetPostResponse getPost(Long postId, Long loginId);

     void editPost(Long postId, MultipartFile file, String postContent) throws IOException;

     void deletePost(Long postId) throws IOException;

     SearchListResponse searchPost(String searchOne, int pageNo);

    PopularPostListResponse popularPostList(Long profileId, int pageNo);
}
