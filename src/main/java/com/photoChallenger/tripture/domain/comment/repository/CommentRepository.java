package com.photoChallenger.tripture.domain.comment.repository;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
