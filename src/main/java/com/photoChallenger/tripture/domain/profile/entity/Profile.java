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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    private Integer profileQ;
    private String profileA;
    private String profileNickname;
    private Integer profileSex;
    private LocalDate profileBirth;
    private String profileImgUrl;
    private String profileImgName;

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

    private Profile(Integer profileQ, String profileA, String profileNickname, Integer profileSex, LocalDate profileBirth, String profileImgUrl, String profileImgName) {
        this.profileQ = profileQ;
        this.profileA = profileA;
        this.profileNickname = profileNickname;
        this.profileSex = profileSex;
        this.profileBirth = profileBirth;
        this.profileImgUrl = profileImgUrl;
        this.profileImgName = profileImgName;
    }

    @Builder
    public static Profile create(Login login, Integer profileQ, String profileA, String profileNickname, Integer profileSex, LocalDate profileBirth, String profileImgUrl, String profileImgName) {
        Profile profile = new Profile(profileQ, profileA, profileNickname, profileSex, profileBirth, profileImgUrl, profileImgName);
        profile.addLogin(login);
        return profile;
    }

    private void addLogin(Login login) {
        this.login = login;
        login.setProfile(this);
    }

    public Profile changeProfile(String profileImgName, String profileImgUrl) {
        this.profileImgName = profileImgName;
        this.profileImgUrl = profileImgUrl;
        return this;
    }
}

