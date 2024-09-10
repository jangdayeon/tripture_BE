package com.photoChallenger.tripture.domain.comment.repository;

import com.photoChallenger.tripture.domain.comment.dto.CommentDto;
import com.photoChallenger.tripture.domain.comment.dto.FindComment;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByProfileId(Long profileId, Pageable pageable);

    @Modifying
    void deleteByProfileId(Long profileId);
    @Modifying
    void deleteByCommentGroupId(Long commentGroupId);

    @Query("SELECT new com.photoChallenger.tripture.domain.comment.dto.CommentDto(c.commentId, c.profileId, p.profileImgName, p.profileNickname, c.commentDate, c.commentContent) " +
            "from Comment c join Profile p on c.profileId = p.profileId " +
            "WHERE c.commentGroupId = :commentId AND c.nested = true")
    List<CommentDto> findAllNestedCommentByCommentId(@Param("commentId") Long commentId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.commentGroupId = :groupId")
    void deleteAllCommentByGroupId(@Param("groupId") Long groupId);

    @Query("select new com.photoChallenger.tripture.domain.comment.dto.CommentDto(c.commentId, c.profileId, p.profileImgName, p.profileNickname, c.commentDate, c.commentContent) " +
            "from Comment c join Profile p on c.profileId = p.profileId " +
            "where c.post.postId= :postId and c.nested = :nested and c.profileId = p.profileId")
    Page<CommentDto> findAllByPost_PostIdAndNested(Long postId, Boolean nested, Pageable pageable);

}
