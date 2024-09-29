package com.photoChallenger.tripture.domain.post.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.post.dto.*;
import com.photoChallenger.tripture.domain.post.service.PostService;
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

    @PutMapping("/edit")
    public ResponseEntity<String> editPost(@RequestBody PostEditRequest postEditRequest) throws IOException {
        postService.editPost(postEditRequest.getPostId(), postEditRequest.getPostContent());
        return ResponseEntity.ok().body("Post modification successful");
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) throws IOException {
        return ResponseEntity.ok().body(postService.deletePost(postId));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchListResponse> searchPost(@RequestParam(required = true) String searchOne,
                            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) throws IOException {
        if(searchOne.isBlank()) {
            throw new InputFieldException("검색어는 필수입니다", HttpStatus.BAD_REQUEST,"post");
        }
        String searchDecoding = URLDecoder.decode(searchOne, "UTF-8").describeConstable().orElseThrow().toLowerCase(Locale.ROOT);
        log.info("검색어 변환 -------------- " + searchDecoding);
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
                                          @RequestParam(required = true) String contentId,
                                          @RequestParam(required = true) String challengeName,
                                          @RequestParam(required = true) String areaCode) throws IOException{
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.newPost(loginIdResponse.getLoginId(), postContent, file, contentId, areaCode, challengeName);
        return ResponseEntity.status(201).body("Post Add Successful");
    }

    @GetMapping("/check/{contentId}")
    public ResponseEntity<Boolean> checkPost(HttpServletRequest request, @PathVariable String contentId) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postService.checkPostExists(loginIdResponse.getLoginId(), contentId));
    }
}
