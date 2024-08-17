package com.photoChallenger.tripture.domain.post.controller;

import com.photoChallenger.tripture.domain.challenge.dto.AroundChallengeRequest;
import com.photoChallenger.tripture.domain.challenge.dto.SurroundingChallengeResponse;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.post.dto.*;
import com.photoChallenger.tripture.domain.post.service.PostService;
import com.photoChallenger.tripture.global.S3.S3Service;
import com.photoChallenger.tripture.global.exception.InputFieldException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @GetMapping("/myPostList")
    public ResponseEntity<MyPostListResponse> findMyPostList(HttpServletRequest request,
                                                             @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) throws IOException{
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postService.findMyPosts(loginIdResponse.getLoginId(), pageNo));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable("postId") Long postId, HttpServletRequest request) throws IOException{
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
    public ResponseEntity<SearchListResponse> searchPost(@RequestParam(required = true) String searchOne,
                            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) throws IOException {
        if(searchOne.isBlank()) {
            throw new InputFieldException("검색어는 필수입니다", HttpStatus.BAD_REQUEST,"post");
        }
        String searchDecoding = URLDecoder.decode(searchOne, "UTF-8").describeConstable().orElseThrow().toLowerCase(Locale.ROOT);
        return ResponseEntity.ok().body(postService.searchPost(searchDecoding, pageNo));
    }

    @GetMapping("/popularPost")
    public ResponseEntity<PopularPostListResponse> popularPost(HttpServletRequest request,@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) throws IOException{
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postService.popularPostList(loginIdResponse.getLoginId(),pageNo));
    }

    @GetMapping("/TopPopularPost")
    public ResponseEntity<List<ChallengePopularPostResponse>> getPopularPost10(HttpServletRequest request,@RequestParam(required = false, defaultValue = "postViewCount", value = "criteria") String properties) throws IOException{
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postService.getPopularPost10(loginIdResponse.getLoginId(),properties));
    }

    @PostMapping("/new")
    public ResponseEntity<String> newPost(HttpServletRequest request,
                                          @RequestParam(required = false) String postContent,
                                          @RequestParam(required = true) MultipartFile file,
                                          @RequestParam(required = true) Long challengeId) throws IOException{
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.newPost(loginIdResponse.getLoginId(), postContent, file, challengeId);
        return ResponseEntity.status(201).body("Post Add Successful");
    }
}
