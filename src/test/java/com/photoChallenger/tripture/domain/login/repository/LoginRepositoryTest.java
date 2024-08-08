package com.photoChallenger.tripture.domain.login.repository;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.entity.ProfileAuth;
import com.photoChallenger.tripture.domain.profile.entity.ProfileLevel;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.global.exception.login.NoSuchEmailException;
import org.junit.jupiter.api.Assertions;
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
class LoginRepositoryTest {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private ProfileRepository profileRepository;

    Long loginId = 0L;

    @BeforeEach
    void setUp() {
        Profile profile = Profile.builder()
                .profileImgName(null)
                .profileNickname("gam")
                .profileLevel(ProfileLevel.LEVEL3)
                .profileTotalPoint(0)
                .profileAuth(ProfileAuth.USER)
                .build();

        Login login = Login.builder()
                .loginEmail("gam@gmail.com")
                .loginPw("gam1221")
                .loginType(LoginType.SELF)
                .profile(profile)
                .build();

        Profile saveProfile = profileRepository.save(profile);
        Login saveLogin = loginRepository.save(login);
        loginId = saveLogin.getLoginId();
    }
    @Test
    void findByEmailWithTypeTest() {
        //when
        Login login = loginRepository.findByEmailWithType("gam@gmail.com", LoginType.SELF).orElseThrow(NoSuchEmailException::new);

        //then
        org.assertj.core.api.Assertions.assertThat(login.getLoginId()).isEqualTo(loginId);
    }

}