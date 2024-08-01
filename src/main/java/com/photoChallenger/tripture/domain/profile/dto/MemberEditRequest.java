package com.photoChallenger.tripture.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditRequest {
    String profileNickname;
    MultipartFile file;
    String loginPw;

    public static MemberEditRequest of(String profileNickname, MultipartFile multipartFile, String loginPw){
        return new MemberEditRequest(profileNickname, multipartFile, loginPw);
    }
}
