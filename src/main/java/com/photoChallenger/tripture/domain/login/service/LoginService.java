package com.photoChallenger.tripture.domain.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.photoChallenger.tripture.domain.login.dto.KakaoProfileResponse;
import com.photoChallenger.tripture.domain.login.dto.SaveLoginRequest;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;

import java.time.LocalDateTime;

public interface LoginService {

    /**
     * 회원 가입 (로그인)
     */
    LoginIdResponse saveLogin(SaveLoginRequest request);

    /**
     * 회원 로그인
     */
    LoginIdResponse memberLogin(String email, String pw);

    /**
     * 카카오 로그인 토큰 가져오기
     */
    String getOAuthToken(String code) throws JsonProcessingException;

    /**
     * 카카오 로그인 유저 정보 가져오기
     */
    LoginIdResponse getUserInfo(String accessToken) throws JsonProcessingException;

    /**
     * 로그인 세션 정보 저장
     */
    void autoLogin(Long loginId, String sessionId, LocalDateTime sessionLimit);

    /**
     * 쿠키를 이용한 로그인 정보 조회
     */
    LoginIdResponse checkLoginId(String sessionId);

    /**
     * 로그아웃
     */
    void logout(Long loginId);
}
