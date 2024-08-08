package com.photoChallenger.tripture.domain.profile.dto;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.global.S3.S3Url;
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

    public static MemberEditForm from(Login login){
        return new MemberEditForm(login.getProfile().getProfileNickname(), S3Url.S3_URL+login.getProfile().getProfileImgName(),login.getLoginType().toString(),login.getLoginEmail(),login.getLoginPw());
    }
}
