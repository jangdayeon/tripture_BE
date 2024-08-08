package com.photoChallenger.tripture.domain.postLike.entity;

import com.photoChallenger.tripture.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long postLikeId;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private PostLike(Long profileId) {
        this.profileId = profileId;
    }

    @Builder
    public static PostLike create(Post post, Long profileId){
        PostLike postLike = new PostLike(profileId);
        postLike.addPost(post);
        return postLike;
    }

    private void addPost(Post post){
        this.post = post;
        post.getPostLike().add(this);
    }

    public void remove(Post post){
        post.getPostLike().remove(this);
    }
}
