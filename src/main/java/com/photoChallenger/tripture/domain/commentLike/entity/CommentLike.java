package com.photoChallenger.tripture.domain.commentLike.entity;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long commentLikeId;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private CommentLike(Long commentLikeId, Long profileId) {
        this.commentLikeId = commentLikeId;
        this.profileId = profileId;
    }

    @Builder
    public static CommentLike create(Comment comment, Long commentLikeId, Long profileId){
        CommentLike commentLike = new CommentLike(commentLikeId,profileId);
        commentLike.addComment(comment);
        return commentLike;
    }

    private void addComment(Comment comment){
        this.comment = comment;
        comment.getCommentLike().add(this);
    }

    public void remove(Comment comment){
        comment.getCommentLike().remove(this);
    }
}
