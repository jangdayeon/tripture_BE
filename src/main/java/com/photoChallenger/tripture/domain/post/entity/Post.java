package com.photoChallenger.tripture.domain.post.entity;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.challenge.entity.ChallengeRegion;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.Inet4Address;
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
    private String postImgName;

    @Column(columnDefinition = "longtext")
    private String postContent;

    @Column(nullable = false)
    private LocalDate postDate;

    @Column(nullable = false)
    private Integer postLikeCount;

    @Column(nullable = false)
    private Long postViewCount;

    @Column(length = 255)
    private String contentId;

    @Column(length = 255)
    private String postChallengeName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private ChallengeRegion postChallengeRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLike = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();


    private Post(String postImgName, String postContent, LocalDate postDate, Integer postLikeCount, Long postViewCount, String contentId, String postChallengeName, ChallengeRegion postChallengeRegion) {
        this.postImgName = postImgName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postLikeCount = postLikeCount;
        this.postViewCount = postViewCount;
        this.contentId = contentId;
        this.postChallengeName = postChallengeName;
        this.postChallengeRegion = postChallengeRegion;
    }

    @Builder
    public static Post create(Profile profile, String postImgName, String postContent, LocalDate postDate, Integer postLikeCount, Long postViewCount, String contentId, String postChallengeName, ChallengeRegion postChallengeRegion){
        Post post = new Post(postImgName, postContent, postDate, postLikeCount, postViewCount, contentId, postChallengeName, postChallengeRegion);
        post.addProfile(profile);
        return post;
    }

    public void update(String postImgName, String postContent, LocalDate postDate) {
        this.postImgName = postImgName;
        this.postContent = postContent;
        this.postDate = postDate;
    }

    public void update(String postContent, LocalDate postDate) {
        this.postContent = postContent;
        this.postDate = postDate;
    }

    public void subtractLikeCount() {
        this.postLikeCount -= 1;
    }

    public void likeCountRedis(Integer like) { this.postLikeCount = like; }

    public void viewCountRedis(Long viewCount) {
        this.postViewCount = viewCount;
    }

    private void addProfile(Profile profile){
        this.profile = profile;
        profile.getPost().add(this);
    }

    public void remove(Profile profile){
        profile.getPost().remove(this);
    }

}
