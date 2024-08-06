package com.photoChallenger.tripture.domain.comment.repository;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByProfileId(Long profileId, Pageable pageable);
}
