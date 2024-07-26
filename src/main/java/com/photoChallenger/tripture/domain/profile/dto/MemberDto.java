package com.photoChallenger.tripture.domain.profile.dto;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String profileNickname;
    private String profileImgName;
    private String loginEmail;

    public static MemberDto of(String profileNickname, String profileImgName, String loginEmail){
        return MemberDto.builder()
                .profileNickname(profileNickname)
                .profileImgName(profileImgName)
                .loginEmail(loginEmail)
                .build();
    }

    public static MemberDto from(Login login){
        Profile profile = login.getProfile();
        return new MemberDto(profile.getProfileNickname(), "https://tripture.s3.ap-northeast-2.amazonaws.com/"+profile.getProfileImgName(), login.getLoginEmail());
    }
}
