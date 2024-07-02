package com.photoChallenger.tripture.domain.post.entity;

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
    private Long postId;

    @Column(length = 255)
    private String postImgUrl;

    @Column(length = 255)
    private String postImgName;

    @Column(columnDefinition = "longtext")
    private String postContent;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLike = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();

    private Post(Long postId, String postImgUrl, String postImgName, String postContent, LocalDate postDate, Integer postLikeCount, Integer postViewCount, String contentId) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.postImgName = postImgName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postLikeCount = postLikeCount;
        this.postViewCount = postViewCount;
        this.contentId = contentId;
    }

    @Builder
    public static Post create(Profile profile, Long postId, String postImgUrl, String postImgName, String postContent, LocalDate postDate, Integer postLikeCount, Integer postViewCount, String contentId){
        Post post = new Post(postId, postImgUrl, postImgName, postContent, postDate, postLikeCount, postViewCount, contentId);
        post.addProfile(profile);
        return post;
    }

    private void addProfile(Profile profile){
        this.profile = profile;
        profile.getPost().add(this);
    }

    public void remove(Profile profile){
        profile.getPost().remove(this);
    }

}
