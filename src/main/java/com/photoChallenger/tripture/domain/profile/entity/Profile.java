package com.photoChallenger.tripture.domain.profile.entity;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.point.entity.Point;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long profileId;

    private Integer profileQ;

    @Column(length = 255)
    private String profileA;

    @Column(length = 255)
    private String profileNickname;

    private Integer profileSex;

    private LocalDate profileBirth;

    @Column(length = 255)
    private String profilePhone;

    @Column(length = 255)
    private String profileImgUrl;

    @Column(length = 255)
    private String profileImgName;

    @Enumerated(value = EnumType.STRING)
    private ProfileLevel profileLevel;

    @Enumerated(value = EnumType.STRING)
    private ProfileAuth profileAuth;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private Login login;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    private Profile(Integer profileQ, String profileA, String profileNickname, Integer profileSex, LocalDate profileBirth, String profilePhone, String profileImgUrl, String profileImgName, ProfileLevel profileLevel, ProfileAuth profileAuth) {
        this.profileQ = profileQ;
        this.profileA = profileA;
        this.profileNickname = profileNickname;
        this.profileSex = profileSex;
        this.profileBirth = profileBirth;
        this.profilePhone = profilePhone;
        this.profileImgUrl = profileImgUrl;
        this.profileImgName = profileImgName;
        this.profileLevel = profileLevel;
        this.profileAuth = profileAuth;
    }

    @Builder
    public static Profile create(Login login, Integer profileQ, String profileA, String profileNickname, Integer profileSex, LocalDate profileBirth, String profilePhone, String profileImgUrl, String profileImgName, ProfileLevel profileLevel, ProfileAuth profileAuth) {
        Profile profile = new Profile(profileQ, profileA, profileNickname, profileSex, profileBirth,profilePhone, profileImgUrl, profileImgName, profileLevel, profileAuth);
        profile.addLogin(login);
        return profile;
    }

    private void addLogin(Login login) {
        this.login = login;
        login.setProfile(this);
    }

    public Profile changeProfile(String profileImgName, String profileImgUrl, ProfileLevel profileLevel) {
        this.profileImgName = profileImgName;
        this.profileImgUrl = profileImgUrl;
        this.profileLevel = profileLevel;
        return this;
    }
}

