package com.photoChallenger.tripture.domain.profile.dto;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
        return new MemberDto(profile.getProfileNickname(), S3Url.S3_URL+profile.getProfileImgName(), login.getLoginEmail());
    }
}
