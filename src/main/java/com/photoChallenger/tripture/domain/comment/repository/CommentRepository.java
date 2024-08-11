package com.photoChallenger.tripture.domain.comment.repository;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByProfileId(Long profileId, Pageable pageable);

    @Modifying
    void deleteByProfileId(Long profileId);
    @Modifying
    void deleteByCommentGroupId(Long commentGroupId);
}
