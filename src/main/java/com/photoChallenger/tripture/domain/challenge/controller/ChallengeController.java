package com.photoChallenger.tripture.domain.challenge.controller;

import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.service.ChallengeService;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/check/{contentId}")
    public ResponseEntity<Boolean> checkContentId(@PathVariable String contentId) {
        boolean photoChallengeExist = challengeService.isPhotoChallengeExist(contentId);
        return ResponseEntity.ok().body(photoChallengeExist);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<GetPhotoChallengeResponse> getPhotoChallenge(HttpServletRequest request, @PathVariable String contentId) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);

        GetPhotoChallengeResponse photoChallenge = challengeService.getPhotoChallenge(contentId, loginIdResponse.getLoginId());
        return ResponseEntity.ok().body(photoChallenge);
    }
}
