package com.photoChallenger.tripture.domain.post.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.post.dto.GetPostResponse;
import com.photoChallenger.tripture.domain.post.dto.MyPostResponse;
import com.photoChallenger.tripture.domain.post.dto.SearchListResponse;
import com.photoChallenger.tripture.domain.post.dto.SearchResponse;
import com.photoChallenger.tripture.domain.post.service.PostService;
import com.photoChallenger.tripture.global.S3.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @GetMapping("/myPostList")
    public ResponseEntity<List<MyPostResponse>> findMyPostList(HttpServletRequest request,
                                                               @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postService.findMyPosts(loginIdResponse.getLoginId(), pageNo));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable("postId") Long postId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postService.getPost(postId, loginIdResponse.getLoginId()));
    }

    @PutMapping("/edit/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId,
                                           @RequestParam(required = false) MultipartFile file,
                                           @RequestParam String postContent) throws IOException {
        postService.editPost(postId, file, postContent);
        return ResponseEntity.ok().body("Post modification successful");
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) throws IOException {
        postService.deletePost(postId);
        return ResponseEntity.ok().body("Post Deletion Successful");
    }

    @GetMapping("/search")
    public ResponseEntity<SearchListResponse> searchPost(@RequestParam(required = false) String searchOne){
        return ResponseEntity.ok().body(postService.searchPost(searchOne));
    }
}
