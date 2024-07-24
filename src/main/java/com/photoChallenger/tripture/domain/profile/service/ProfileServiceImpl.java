package com.photoChallenger.tripture.domain.profile.service;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.profile.dto.MemberDto;
import com.photoChallenger.tripture.domain.profile.dto.MemberEditForm;
import com.photoChallenger.tripture.domain.profile.dto.MemberEditRequest;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final LoginRepository loginRepository;
    @Override
    public MemberDto getMember(Long LoginId){
        Login login = loginRepository.findById(LoginId).get();
        return MemberDto.from(login);
    }

    @Override
    public MemberEditForm memberEditForm(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        return MemberEditForm.from(login);
    }

    @Override
    @Transactional
    public void memberEdit(String profileImgName, String profileNickname, String loginPw, long loginId) {
        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();
        profile.update(profileImgName,profileNickname);
        if (login.getLoginType().equals(LoginType.SELF)){
            login.update(loginPw);
        }
    }

    @Override
    public String checkLevel(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        return login.getProfile().getProfileLevel().toString();
    }

    @Override
    public String checkProfileImgName(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        return login.getProfile().getProfileImgName() != "default"? login.getProfile().getProfileImgName():null;
        }
    }
