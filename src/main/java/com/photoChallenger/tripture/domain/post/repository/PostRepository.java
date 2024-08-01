package com.photoChallenger.tripture.domain.post.repository;

import com.photoChallenger.tripture.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByProfile_ProfileIdOrderByPostDateAsc(Long profileId);
}
