package com.photoChallenger.tripture.domain.login.controller;

import com.photoChallenger.tripture.domain.login.dto.*;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.login.service.LoginService;
import com.photoChallenger.tripture.domain.login.service.MailAuthenticationService;
import com.photoChallenger.tripture.global.S3.S3Service;
import com.photoChallenger.tripture.global.exception.login.EmailAuthenticationIssuesException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.photoChallenger.tripture.domain.login.dto.*;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.login.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.photoChallenger.tripture.domain.login.entity.SessionConst.SESSION_COOKIE_NAME;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    private final MailAuthenticationService mailAuthenticationService;
    private final S3Service s3Service;

    /**
     * 회원 등록
     */
    @PostMapping("/new")
    public ResponseEntity<String> loginRegister(@RequestParam String loginEmail,
                                                         @RequestParam String loginPw,
                                                         @RequestParam(required = false) MultipartFile file,
                                                         @RequestParam String nickname,
                                                         @RequestParam LoginType loginType, HttpServletRequest request) throws IOException {
        String profileImgName = "default";

        if(!file.isEmpty()) {
            profileImgName = s3Service.upload(file, "profile");
        }

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
    @PostMapping("/{isAutoLogin}")
    public ResponseEntity<String> memberLogin(@PathVariable String isAutoLogin, @RequestBody LoginRequest loginRequest
            , HttpServletRequest request, HttpServletResponse response) {
        LoginIdResponse loginIdResponse = loginService.memberLogin(loginRequest.getLoginEmail(), loginRequest.getLoginPw());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginIdResponse);

        if(isAutoLogin.equals("true")) {
            int amount = 60*60*24*90; // 90일

            Cookie cookie = new Cookie(SESSION_COOKIE_NAME, session.getId());
            cookie.setPath("/");
            cookie.setMaxAge(amount);
            response.addCookie(cookie);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime sessionLimit = now.plusDays(90);

            loginService.autoLogin(loginIdResponse.getLoginId(), session.getId(), sessionLimit);
        } else {
            loginService.autoLogin(loginIdResponse.getLoginId(), session.getId(), LocalDateTime.now());
        }

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
