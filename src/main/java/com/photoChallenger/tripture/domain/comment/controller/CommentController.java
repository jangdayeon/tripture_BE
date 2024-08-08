package com.photoChallenger.tripture.domain.comment.controller;

import com.photoChallenger.tripture.domain.comment.dto.FindNestedAllComment;
import com.photoChallenger.tripture.domain.comment.dto.MyCommentResponse;
import com.photoChallenger.tripture.domain.comment.dto.WriteCommentRequest;
import com.photoChallenger.tripture.domain.comment.service.CommentService;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    //작성한 댓글 리스트
    @GetMapping("/myCommentList")
    public ResponseEntity<List<MyCommentResponse>> myCommentList(HttpServletRequest request,
                                                                 @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(commentService.findMyComments(loginIdResponse.getLoginId(), pageNo));
    }

    @PostMapping("")
    public ResponseEntity<String> writeComment(HttpServletRequest request,
                                               @RequestBody WriteCommentRequest writeCommentRequest) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);

        commentService.writeComment(writeCommentRequest, loginIdResponse.getLoginId());
        return ResponseEntity.ok().body("Comment successfully written");
    }

    @GetMapping("/nested/{groupId}")
    public ResponseEntity<FindNestedAllComment> nestedAllComment(@PathVariable Long groupId) {
        return ResponseEntity.ok().body(commentService.findAllNestedComment(groupId));
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body("Successfully deleted comments");
    }
}
