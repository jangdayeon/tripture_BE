package com.photoChallenger.tripture.domain.comment.entity;

import com.photoChallenger.tripture.domain.commentLike.entity.CommentLike;
import com.photoChallenger.tripture.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "LONGTEXT")
    private String commentContent;

    @Column(nullable = false)
    private Integer commentLikeCount;

    @Column(nullable = false)
    private LocalDateTime commentDate;

    @Column(updatable = false)
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLike = new ArrayList<>();

    private Comment(Long commentId, String commentContent, Integer commentLikeCount, LocalDateTime commentDate, Long profileId) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentLikeCount = commentLikeCount;
        this.commentDate = commentDate;
        this.profileId = profileId;
    }

    @Builder
    public static Comment create(Post post, Long commentId, String commentContent, Integer commentLikeCount, LocalDateTime commentDate, Long profileId){
        Comment comment = new Comment(commentId, commentContent, commentLikeCount, commentDate, profileId);
        comment.addPost(post);
        return comment;
    }

    private void addPost(Post post){
        this.post = post;
        post.getComment().add(this);
    }

    public Comment changeCommentContent(String commentContent){
        this.commentContent = commentContent;
        this.commentDate = LocalDateTime.now();
        return this;
    }

    public Comment addCommentLikeCount(){
        this.commentLikeCount += 1;
        return this;
    }

    public Comment cancelCommentLikeCount(){
        this.commentLikeCount -=1;
        return this;
    }

    public void remove(Post post){
        post.getComment().remove(this);
    }
}
