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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    @PostMapping("/edit")
    public ResponseEntity<String> editMember(HttpServletRequest request,
                                             @RequestParam(required = false) String profileNickname,
                                             @RequestParam(required = false) MultipartFile file,
                                             @RequestParam(required = false) String loginPw) throws IOException {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        MemberEditRequest memberEditRequest = new MemberEditRequest(profileNickname,file,loginPw);

        String imgName = "default";
        // 사진 등록
        if(!memberEditRequest.getFile().isEmpty()) {
            try {
                String checkProfileImgName = profileService.checkProfileImgName(loginIdResponse.getLoginId());
                if (checkProfileImgName != null){
                    s3Service.delete(checkProfileImgName);
                }
                imgName = s3Service.upload(memberEditRequest.getFile(), "file");
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

    //회원탈퇴
    @GetMapping("/delete")
    public ResponseEntity<String> deleteProfile(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        profileService.deleteOne(loginIdResponse.getLoginId());
        removeSessionValue(session);
        return ResponseEntity.ok().body("profile deletion successful");
    }

    private void removeSessionValue(HttpSession session) { //세션 삭제
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        session.removeAttribute(SessionConst.SESSION_COOKIE_NAME);
        session.invalidate(); //관련된 모든 session 속성 삭제
    }

}
