package com.photoChallenger.tripture.domain.profile.entity;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.postCnt.entity.PostCnt;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.point.entity.Point;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long profileId;

    @Column(length = 255)
    private String profileNickname;

    @Column(length = 255)
    private String profileImgName;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer profileTotalPoint;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private ProfileLevel profileLevel;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private ProfileAuth profileAuth;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Login> login = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private PostCnt postCnt;

    private Profile(String profileNickname, String profileImgName, ProfileLevel profileLevel, ProfileAuth profileAuth, Integer profileTotalPoint) {
        this.profileNickname = profileNickname;
        this.profileImgName = profileImgName;
        this.profileLevel = profileLevel;
        this.profileAuth = profileAuth;
        this.profileTotalPoint = profileTotalPoint;
    }

    @Builder
    public static Profile create(String profileNickname, String profileImgName, ProfileLevel profileLevel, ProfileAuth profileAuth, Integer profileTotalPoint) {
        Profile profile = new Profile(profileNickname, profileImgName, profileLevel, profileAuth, profileTotalPoint);
        profile.addPostCnt(PostCnt.create());
        return profile;
    }

    private void addPostCnt(PostCnt postCnt) {
        this.postCnt = postCnt;
        postCnt.setProfile(this);
    }

    public Profile update(String profileImgName, String profileNickname) {
        this.profileImgName = profileImgName;
        this.profileNickname = profileNickname;
        return this;
    }

    public Profile update(ProfileLevel profileLevel){
        this.profileLevel = profileLevel;
        return this;
    }

    public Profile update(Integer profileTotalPoint){
        this.profileTotalPoint = profileTotalPoint;
        return this;
    }
}

