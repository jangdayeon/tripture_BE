package com.photoChallenger.tripture.domain.login.service;

import com.photoChallenger.tripture.domain.login.dto.SaveLoginRequest;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.entity.ProfileAuth;
import com.photoChallenger.tripture.domain.profile.entity.ProfileLevel;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.global.exception.login.DuplicateEmailException;
import com.photoChallenger.tripture.global.exception.login.IdPasswordMismatchException;
import com.photoChallenger.tripture.global.exception.login.NoSuchEmailException;
import com.photoChallenger.tripture.global.exception.profile.DuplicateNicknameException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;
    private final ProfileRepository profileRepository;

    /**
     * 회원 가입 (로그인)
     */
    @Transactional
    public LoginIdResponse saveLogin(SaveLoginRequest request) {
        validateDuplicateMember(request.getLoginEmail());
        validateDuplicateNickname(request.getNickname());

        Profile profile = Profile.builder().
                profileImgName(request.getProfileImgName()).
                profileNickname(request.getNickname()).
                profileLevel(ProfileLevel.BEGINNER).
                profileAuth(ProfileAuth.USER).
                profileTotalPoint(0).build();

        Login login = Login.builder().
                loginEmail(request.getLoginEmail()).
                loginPw(request.getLoginPw()).
                loginType(request.getLoginType()).
                profile(profile).build();

        Profile profileSave = profileRepository.save(profile);
        Login loginSave = loginRepository.save(login);

        return new LoginIdResponse(loginSave.getLoginId());
    }

    /**
     * 이미 사용 중인 이메일인 경우 -> KAKAO, SELF 둘 다 회원가입 되어있는지 확인
     * 둘 다 되어 있음 -> 오류
     */
    private void validateDuplicateMember(String email) {
        if(loginRepository.countByLoginEmail(email) >= 2) {
            throw new DuplicateEmailException();
        }
    }

    /**
     * 닉네임 중복 확인
     */
    private void validateDuplicateNickname(String nickname) {
        if(profileRepository.existsByProfileNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    /**
     * 회원 로그인
     */
    public LoginIdResponse memberLogin(String email, String pw) {
        Login login = loginRepository.findByEmailWithType(email, LoginType.SELF).orElseThrow(NoSuchEmailException::new);

        if(!pw.equals(login.getLoginPw())) {
            throw new IdPasswordMismatchException();
        }

        return new LoginIdResponse(login.getLoginId());
    }

}
