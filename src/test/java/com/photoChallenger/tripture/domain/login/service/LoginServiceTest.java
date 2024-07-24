package com.photoChallenger.tripture.domain.login.service;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.entity.ProfileAuth;
import com.photoChallenger.tripture.domain.profile.entity.ProfileLevel;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class LoginServiceTest {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private LoginService loginService;

    Long loginId = 0L;

    @BeforeEach
    void setUp() {
        Profile profile = Profile.builder()
                .profileImgName(null)
                .profileNickname("gamja")
                .profileLevel(ProfileLevel.BEGINNER)
                .profileTotalPoint(0)
                .profileAuth(ProfileAuth.USER)
                .build();

        Login login = Login.builder()
                .loginEmail("gamja@gmail.com")
                .loginPw("gamja1221")
                .loginType(LoginType.SELF)
                .profile(profile)
                .build();

        Profile saveProfile = profileRepository.save(profile);
        Login saveLogin = loginRepository.save(login);
        loginId = saveLogin.getLoginId();
    }

    @Test
    void memberLoginTest() {
        //when
        LoginIdResponse loginIdResponse = loginService.memberLogin("gamja@gmail.com", "gamja1221");

        //then
        Assertions.assertThat(loginIdResponse.getLoginId()).isEqualTo(loginId);
    }

}