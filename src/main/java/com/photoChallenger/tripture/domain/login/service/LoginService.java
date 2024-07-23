package com.photoChallenger.tripture.domain.login.service;

import com.photoChallenger.tripture.domain.login.dto.SaveLoginRequest;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;

public interface LoginService {

    /**
     * 회원 가입 (로그인)
     */
    LoginIdResponse saveLogin(SaveLoginRequest request);

    /**
     * 회원 로그인
     */
    LoginIdResponse memberLogin(String email, String pw);
}
