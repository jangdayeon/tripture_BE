package com.photoChallenger.tripture.domain.login.controller;

import com.photoChallenger.tripture.domain.login.dto.*;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.login.service.LoginService;
import com.photoChallenger.tripture.domain.login.service.MailAuthenticationService;
import com.photoChallenger.tripture.global.exception.login.EmailAuthenticationIssuesException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.photoChallenger.tripture.domain.login.dto.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    private final MailAuthenticationService mailAuthenticationService;

    /**
     * 회원 등록
     */
    @PostMapping("/new")
    public ResponseEntity<String> loginRegister(@RequestParam String loginEmail,
                                                         @RequestParam String loginPw,
                                                         @RequestParam(required = false) MultipartFile file,
                                                         @RequestParam String nickname,
                                                         @RequestParam LoginType loginType, HttpServletRequest request) {
        // 사진 저장 로직 -> 추후 name 받은 후, request 넘겨줌
        String profileImgName = "default";
        LoginIdResponse loginIdResponse = loginService.saveLogin(SaveLoginRequest.builder()
                .loginEmail(loginEmail)
                .loginPw(loginPw)
                .profileImgName(profileImgName)
                .nickname(nickname)
                .loginType(loginType)
                .build());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginIdResponse);

        return ResponseEntity.ok().body("User register success");
    }

    /**
     * 회원 로그인
     */
    @PostMapping("")
    public ResponseEntity<String> memberLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        LoginIdResponse loginIdResponse = loginService.memberLogin(loginRequest.getLoginEmail(), loginRequest.getLoginPw());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginIdResponse);

        return ResponseEntity.ok().body("User login success");
    }

    /**
     * 인증 이메일 발송
     */
    @PostMapping ("/mailSend")
    public String mailSend(@RequestBody @Valid EmailAuthRequest emailAuthRequest){
        return mailAuthenticationService.createMail(emailAuthRequest.getEmail());
    }

    /**
     * 인증번호 일치 여부
     */
    @PostMapping("/mailAuthCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        boolean Checked = mailAuthenticationService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
        if(Checked){
            return "Email authentication success";
        }
        else{
            throw new EmailAuthenticationIssuesException();
        }
    }

    /**
     * 카카오 로그인
     */
    @GetMapping("/kakao-login")
    public ResponseEntity<String> doSocialLogin(@RequestParam("code") String code, HttpServletRequest request) throws JsonProcessingException {
        String oAuthToken = loginService.getOAuthToken(code);
        LoginIdResponse userInfo = loginService.getUserInfo(oAuthToken);

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, userInfo);

        return ResponseEntity.ok().body("Kakao login success");
    }

}
