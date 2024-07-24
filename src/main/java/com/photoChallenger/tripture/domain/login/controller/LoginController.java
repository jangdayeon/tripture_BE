package com.photoChallenger.tripture.domain.login.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginRequest;
import com.photoChallenger.tripture.domain.login.dto.SaveLoginRequest;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.login.service.LoginService;
import com.photoChallenger.tripture.domain.login.service.MailAuthenticationService;
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
    public ResponseEntity<LoginIdResponse> loginRegister(@RequestParam String loginEmail,
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

    /**
     * 인증 이메일 발송
     */
    @PostMapping("/mailSend")
    public Map<String, Object> mailSend(String mail) {
        Map<String, Object> map = new ConcurrentHashMap<>();

        try {
            String num = String.valueOf(mailAuthenticationService.sendMail(mail));

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    // 인증번호 일치여부 확인
    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {

        //Redis 연결 후 check 로직 추가
        boolean isMatch = userNumber.equals(String.valueOf(15263));

        return ResponseEntity.ok(isMatch);
    }
}
