package com.photoChallenger.tripture.domain.profile.entity;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
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

    private Profile(Integer profileQ, String profileA, String profileNickname, Integer profileSex, LocalDate profileBirth, String profileImgUrl, String profileImgName) {
        this.profileQ = profileQ;
        this.profileA = profileA;
        this.profileNickname = profileNickname;
        this.profileSex = profileSex;
        this.profileBirth = profileBirth;
        this.profileImgUrl = profileImgUrl;
        this.profileImgName = profileImgName;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private Login login;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();



}

