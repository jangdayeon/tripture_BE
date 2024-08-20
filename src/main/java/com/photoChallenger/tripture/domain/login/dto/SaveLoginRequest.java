package com.photoChallenger.tripture.domain.login.dto;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveLoginRequest {
    String loginEmail;
    String loginPw;
    String profileImgName;
    String nickname;
    LoginType loginType;
    boolean emailAuthCheck;

    @Builder
    public static SaveLoginRequest of(String loginEmail, String loginPw, String profileImgName, String nickname, LoginType loginType, boolean emailAuthCheck) {
        return new SaveLoginRequest(
                loginEmail, loginPw, profileImgName, nickname, loginType, emailAuthCheck
        );
    }
}
