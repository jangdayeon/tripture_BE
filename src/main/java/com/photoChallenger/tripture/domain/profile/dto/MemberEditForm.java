package com.photoChallenger.tripture.domain.profile.dto;

import com.photoChallenger.tripture.domain.login.entity.Login;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditForm {
    String profileNickname;
    String profileImgName;
    String loginType;
    String loginEmail;
    String loginPw;

    @Value("${cloud.aws.url}")
    static String domain;

    public static MemberEditForm from(Login login){
        return new MemberEditForm(login.getProfile().getProfileNickname(),domain+login.getProfile().getProfileImgName(),login.getLoginType().toString(),login.getLoginEmail(),login.getLoginPw());
    }
}
