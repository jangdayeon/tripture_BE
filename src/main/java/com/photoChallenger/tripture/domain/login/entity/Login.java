package com.photoChallenger.tripture.domain.login.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Login {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long loginId;

    @Column(nullable = false, length = 320)
    private String loginEmail;

    @Column(nullable = false, length = 30)
    private String loginPw;

    @OneToOne(mappedBy = "login", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    private Login(String loginEmail, String loginPw){
        this.loginEmail = loginEmail;
        this.loginPw = loginPw;

    }

    @Builder
    public static Login create(String loginEmail, String loginPw){
        Login login = new Login(loginEmail,loginPw);
        return login;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }
}
