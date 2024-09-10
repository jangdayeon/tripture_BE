package com.photoChallenger.tripture.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditResponse {
    String profileNickname;
    String profileImgName;

    public static MemberEditResponse of(String profileNickname, String profileImgName){
        return new MemberEditResponse(profileNickname, profileImgName);
    }
}
