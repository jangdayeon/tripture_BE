package com.photoChallenger.tripture.domain.login.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginRequest;
import com.photoChallenger.tripture.domain.login.dto.SaveLoginRequest;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    /**
     * 회원 등록
     */
    @PostMapping("/new")
    public ResponseEntity<LoginIdResponse> loginRegister(@RequestBody @Valid SaveLoginRequest saveLoginRequest, HttpServletRequest request) {
        LoginIdResponse loginIdResponse = loginService.saveLogin(saveLoginRequest);

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginIdResponse);

        return ResponseEntity.ok().body(loginIdResponse);
    }

    /**
     * 회원 로그인
     */
    @PostMapping("")
    public ResponseEntity<LoginIdResponse> memberLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        LoginIdResponse loginIdResponse = loginService.memberLogin(loginRequest.getLoginEmail(), loginRequest.getLoginPw());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginIdResponse);

        return ResponseEntity.ok().body(loginIdResponse);
    }

    /**
     * 세션 확인
     */
    @GetMapping("/id")
    public ResponseEntity<LoginIdResponse> sessionCheck(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session == null) {
            return new ResponseEntity<>(null, HttpStatus.FOUND);
        }

        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginIdResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.FOUND);
        }

        return ResponseEntity.ok().body(loginIdResponse);
    }
}
