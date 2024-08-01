package com.photoChallenger.tripture.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    String loginEmail;
    String loginPw;
    Boolean isAutoLogin;
}
