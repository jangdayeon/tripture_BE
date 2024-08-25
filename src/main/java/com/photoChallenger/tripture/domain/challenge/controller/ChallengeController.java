package com.photoChallenger.tripture.domain.challenge.controller;

import com.photoChallenger.tripture.domain.challenge.dto.AroundChallengeAllListResponse;
import com.photoChallenger.tripture.domain.challenge.dto.AroundChallengeRequest;
import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.challenge.dto.SurroundingChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.service.ChallengeService;
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
@RequestMapping("/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/check/{contentId}")
    public ResponseEntity<Boolean> checkContentId(@PathVariable String contentId) {
        boolean photoChallengeExist = challengeService.isPhotoChallengeExist(contentId);
        return ResponseEntity.ok().body(photoChallengeExist);
    }

    @GetMapping("/contentId/{contentId}")
    public ResponseEntity<GetPhotoChallengeResponse> getPhotoChallenge(HttpServletRequest request, @PathVariable String contentId) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);

        GetPhotoChallengeResponse photoChallenge = challengeService.getPhotoChallenge(contentId, loginIdResponse.getLoginId());
        return ResponseEntity.ok().body(photoChallenge);
    }

    @GetMapping("/challengeId/{challengeId}")
    public ResponseEntity<GetPhotoChallengeResponse> getPhotoChallenge(HttpServletRequest request, @PathVariable Long challengeId) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);

        GetPhotoChallengeResponse photoChallenge = challengeService.getPhotoChallenge(challengeId, loginIdResponse.getLoginId());
        return ResponseEntity.ok().body(photoChallenge);
    }

    @PostMapping("/area_list")
    public ResponseEntity<AroundChallengeAllListResponse> getAroundChallengeList(@RequestBody AroundChallengeRequest aroundChallengeRequest) {
//        log.info("lat: {}, lon: {}",aroundChallengeRequest.getLat(), aroundChallengeRequest.getLon());
        List<Challenge> aroundChallengeList = challengeService.getAroundChallengeList(aroundChallengeRequest.getLat(), aroundChallengeRequest.getLon(), 2.0);
        return ResponseEntity.ok().body(AroundChallengeAllListResponse.from(aroundChallengeList));
    }

    @PostMapping("/TopSurroundingChallenge")
    public ResponseEntity<List<SurroundingChallengeResponse>> getSurroundingChallenge(HttpServletRequest request, @RequestBody AroundChallengeRequest aroundChallengeRequest,
            @RequestParam(required = false, defaultValue = "postViewCount", value = "criteria") String properties){
//        log.info("lat: {}, lon: {}",aroundChallengeRequest.getLat(), aroundChallengeRequest.getLon());
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(challengeService.getSurroundingChallengeList(loginIdResponse.getLoginId(), aroundChallengeRequest.getLat(), aroundChallengeRequest.getLon(), 2.0, properties));
    }
}
