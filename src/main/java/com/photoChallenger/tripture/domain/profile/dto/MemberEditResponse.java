package com.photoChallenger.tripture.domain.profile.dto;

import lombok.*;

import static com.photoChallenger.tripture.global.S3.S3Url.S3_URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditResponse {
    String profileNickname;
    String profileImgName;

    public static MemberEditResponse of(String profileNickname, String profileImgName){
        return new MemberEditResponse(profileNickname, S3_URL+profileImgName);
    }
}
