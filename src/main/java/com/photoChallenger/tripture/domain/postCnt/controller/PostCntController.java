package com.photoChallenger.tripture.domain.postCnt.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.postCnt.dto.PostChallengeCntResponse;
import com.photoChallenger.tripture.domain.postCnt.service.PostCntService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/postCnt")
public class PostCntController {
    private final PostCntService postCntService;

    @GetMapping("/cnt")
    public ResponseEntity<PostChallengeCntResponse> postChallengeCntResponseResponseEntity(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(postCntService.findProfile(loginIdResponse.getLoginId()));
    }
}
