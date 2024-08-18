package com.photoChallenger.tripture.domain.point.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.point.dto.PointDto;
import com.photoChallenger.tripture.domain.point.dto.PointListResponse;
import com.photoChallenger.tripture.domain.point.service.PointService;
import com.photoChallenger.tripture.domain.post.service.PostService;
import com.photoChallenger.tripture.domain.profile.dto.MemberDto;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {
    private final PointService pointService;
    @GetMapping("/list")
    public ResponseEntity<PointListResponse> pointList(HttpServletRequest request,
                                                       @RequestParam(required = false, defaultValue = "0", value = "page") Integer pageNo,
                                                       @RequestParam LocalDate startDate,
                                                       @RequestParam LocalDate endDate ) throws IOException {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(pointService.getPointList(pageNo, startDate, endDate, loginIdResponse.getLoginId()));
    }
}
