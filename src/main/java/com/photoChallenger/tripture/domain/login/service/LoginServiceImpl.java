package com.photoChallenger.tripture.domain.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoChallenger.tripture.domain.login.dto.KakaoProfileResponse;
import com.photoChallenger.tripture.domain.login.dto.SaveLoginRequest;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.entity.ProfileAuth;
import com.photoChallenger.tripture.domain.profile.entity.ProfileLevel;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.global.exception.login.*;
import com.photoChallenger.tripture.global.exception.profile.DuplicateNicknameException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;
    private final ProfileRepository profileRepository;

    /**
     * 회원 가입 (로그인)
     */
    @Transactional
    public LoginIdResponse saveLogin(SaveLoginRequest request) {
        if(!request.isEmailAuthCheck()) {
            throw new NoAuthenticateEmail();
        }

        validateDuplicateMember(request.getLoginEmail(), request.getLoginType());
        validateDuplicateNickname(request.getNickname());

        Profile profile = Profile.builder().
                profileImgName(request.getProfileImgName()).
                profileNickname(request.getNickname()).
                profileLevel(ProfileLevel.LEVEL1).
                profileAuth(ProfileAuth.USER).
                profileTotalPoint(0).build();

        Login login = Login.builder().
                loginEmail(request.getLoginEmail()).
                loginPw(request.getLoginPw()).
                loginType(request.getLoginType()).
                profile(profile).build();

        Profile profileSave = profileRepository.save(profile);
        Login loginSave = loginRepository.save(login);

        return new LoginIdResponse(loginSave.getLoginId());
    }

    /**
     * 이미 사용 중인 이메일인 경우 -> KAKAO, SELF 둘 다 회원가입 되어있는지 확인
     * 둘 다 되어 있음 -> 오류
     */
    private void validateDuplicateMember(String email, LoginType loginType) {
        if(loginRepository.countByLoginEmail(email) >= 2 ||
        loginRepository.findByEmailWithType(email, loginType).isPresent()) {
            throw new DuplicateEmailException();
        }
    }

    /**
     * 닉네임 중복 확인
     */
    private void validateDuplicateNickname(String nickname) {
        if(profileRepository.existsByProfileNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    /**
     * 회원 로그인
     */
    public LoginIdResponse memberLogin(String email, String pw) {
        Login login = loginRepository.findByEmailWithType(email, LoginType.SELF).orElseThrow(NoSuchEmailException::new);

        if(!pw.equals(login.getLoginPw())) {
            throw new IdPasswordMismatchException();
        }

        return new LoginIdResponse(login.getLoginId());
    }

    /**
     * 카카오 로그인 토큰 가져오기
     */
    public String getOAuthToken(String code) throws JsonProcessingException {
        String reqUrl = "https://kauth.kakao.com/oauth/token";

        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "4dc5f824a01db4a79fddfde6958b2cc4");
        params.add("redirect_uri", "http://127.0.0.1:8080/login/kakao-login");
        params.add("code", code);

        //http 바디(params)와 http 헤더(headers)를 가진 엔티티
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //reqUrl로 Http 요청 , POST 방식
        ResponseEntity<String> response =
                rt.exchange(reqUrl, HttpMethod.POST, kakaoTokenRequest, String.class);

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    /**
     * 카카오 로그인 유저 정보 가져오기
     */
    @Transactional
    public LoginIdResponse getUserInfo(String accessToken) throws JsonProcessingException {
        // 사용자 정보 조회
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        //http 헤더(headers)를 가진 엔티티
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        //reqUrl로 Http 요청 , POST 방식
        ResponseEntity<String> response =
                rt.exchange(reqUrl, HttpMethod.POST, kakaoProfileRequest, String.class);

        KakaoProfileResponse kakaoProfileResponse = new KakaoProfileResponse(response.getBody());

        LoginIdResponse loginIdResponse;
        if(loginRepository.findByEmailWithType(kakaoProfileResponse.getEmail(), LoginType.KAKAO).isEmpty()) {
            loginIdResponse = saveLogin(SaveLoginRequest.builder()
                    .loginEmail(kakaoProfileResponse.getEmail())
                    .loginType(LoginType.KAKAO)
                    .profileImgName("default")
                    .nickname(kakaoProfileResponse.getEmail())
                    .build());
        } else {
            Login login = loginRepository.findByEmailWithType(kakaoProfileResponse.getEmail(), LoginType.KAKAO).get();
            loginIdResponse = new LoginIdResponse(login.getLoginId());
        }

        return loginIdResponse;
    }

    @Transactional
    public void autoLogin(Long loginId, String sessionId, LocalDateTime sessionLimit) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        login.update(sessionId, sessionLimit);
    }

    public LoginIdResponse checkLoginId(String sessionId) {
        Login login = loginRepository.findBySessionId(sessionId).orElseThrow(NoSuchSessionIdException::new);
        return new LoginIdResponse(login.getLoginId());
    }

    @Override
    @Transactional
    public void logout(Long loginId) {
        loginRepository.findById(loginId).get().update(null,null);
    }
}
