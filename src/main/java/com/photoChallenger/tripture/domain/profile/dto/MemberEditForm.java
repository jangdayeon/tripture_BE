package com.photoChallenger.tripture.domain.profile.dto;

import com.photoChallenger.tripture.domain.login.entity.Login;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        return new MemberEditForm(login.getProfile().getProfileNickname(),"https://tripture.s3.ap-northeast-2.amazonaws.com/"+login.getProfile().getProfileImgName(),login.getLoginType().toString(),login.getLoginEmail(),login.getLoginPw());
    }
}
