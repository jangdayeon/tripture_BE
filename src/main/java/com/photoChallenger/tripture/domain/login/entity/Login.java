package com.photoChallenger.tripture.domain.login.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Login {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long loginId;

    @Column(nullable = false, length = 320)
    private String loginEmail;

    @Column(length = 30)
    private String loginPw;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10)")
    private LoginType loginType;

    private String sessionId;
    private LocalDateTime sessionLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private Login(String loginEmail, String loginPw, LoginType loginType, String sessionId, LocalDateTime sessionLimit){
        this.loginEmail = loginEmail;
        this.loginPw = loginPw;
        this.loginType = loginType;
        this.sessionId = sessionId;
        this.sessionLimit = sessionLimit;
    }

    @Builder
    public static Login create(Profile profile, String loginEmail, String loginPw, LoginType loginType, String sessionId, LocalDateTime sessionLimit){
        Login login = new Login(loginEmail,loginPw, loginType, sessionId, sessionLimit);
        login.addProfile(profile);
        return login;
    }

    private void addProfile(Profile profile){
        this.profile = profile;
        profile.getLogin().add(this);
    }

    public Login update(String loginPw){
        this.loginPw = loginPw;
        return this;
    }

    public Login update(String sessionId, LocalDateTime sessionLimit) {
        this.sessionId = sessionId;
        this.sessionLimit = sessionLimit;
        return this;
    }
}
