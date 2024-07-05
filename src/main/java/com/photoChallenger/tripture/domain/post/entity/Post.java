package com.photoChallenger.tripture.domain.post.entity;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long postId;

    @Column(length = 255)
    private String postImgUrl;

    @Column(length = 255)
    private String postImgName;

    @Column(columnDefinition = "longtext")
    private String postContent;

    @Column(nullable = false,columnDefinition = "tinyint(1)")
    private Boolean postStatus;

    @Column(nullable = false)
    private LocalDate postDate;

    @Column(nullable = false)
    private Integer postLikeCount;

    @Column(nullable = false)
    private Integer postViewCount;

    @Column(length = 255)
    private String contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLike = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();


    private Post(Long postId, String postImgUrl, String postImgName, String postContent, Boolean postStatus, LocalDate postDate, Integer postLikeCount, Integer postViewCount, String contentId) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.postImgName = postImgName;
        this.postContent = postContent;
        this.postStatus = postStatus;
        this.postDate = postDate;
        this.postLikeCount = postLikeCount;
        this.postViewCount = postViewCount;
        this.contentId = contentId;
    }

    @Builder
    public static Post create(Profile profile, Challenge challenge, Long postId, String postImgUrl, String postImgName, String postContent, Boolean postStatus, LocalDate postDate, Integer postLikeCount, Integer postViewCount, String contentId){
        Post post = new Post(postId, postImgUrl, postImgName, postContent, postStatus, postDate, postLikeCount, postViewCount, contentId);
        post.addProfileAndChallenge(profile,challenge);
        return post;
    }

    private void addProfileAndChallenge(Profile profile, Challenge challenge){
        this.profile = profile;
        this.challenge = challenge;
        profile.getPost().add(this);
        challenge.getPost().add(this);
    }

    public void remove(Profile profile, Challenge challenge){
        profile.getPost().remove(this);
        challenge.getPost().remove(this);
    }

}
