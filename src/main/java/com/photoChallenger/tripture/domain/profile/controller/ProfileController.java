package com.photoChallenger.tripture.domain.profile.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.profile.dto.MemberDto;
import com.photoChallenger.tripture.domain.profile.dto.MemberEditForm;
import com.photoChallenger.tripture.domain.profile.dto.MemberEditRequest;
import com.photoChallenger.tripture.domain.profile.service.ProfileService;
import com.photoChallenger.tripture.global.S3.S3Service;
import com.photoChallenger.tripture.global.exception.global.S3IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final S3Service s3Service;
    //회원 기본 정보 조회
    @GetMapping("/default")
    public ResponseEntity<MemberDto> defaultMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(profileService.getMember(loginIdResponse.getLoginId()));
    }

    //회원 수정 폼
    @GetMapping("/edit")
    public ResponseEntity<MemberEditForm> editMemeberForm(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(profileService.memberEditForm(loginIdResponse.getLoginId()));
    }

    //회원 수정
    @PutMapping("/edit")
    public ResponseEntity<String> editMember(HttpServletRequest request, MemberEditRequest memberEditRequest){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);

        String imgName = "default";
        // 사진 등록
        if(!memberEditRequest.getFile().isEmpty()) {
            try {
                String checkProfileImgName = profileService.checkProfileImgName(loginIdResponse.getLoginId());
                if (checkProfileImgName != null){
                    s3Service.delete(checkProfileImgName);
                }
                imgName = s3Service.upload(memberEditRequest.getFile(), "profile");
            } catch (IOException e){
                throw new S3IOException();
            }
        }

        profileService.memberEdit(imgName, memberEditRequest.getProfileNickname(), memberEditRequest.getLoginPw(), loginIdResponse.getLoginId());
        return new ResponseEntity("redirection request", HttpStatus.SEE_OTHER);
    }

    //챌린저 레벨 조회
    @GetMapping("/checkLevel")
    public ResponseEntity<String> checkChallengeLevel(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(profileService.checkLevel(loginIdResponse.getLoginId()));
    }
}
