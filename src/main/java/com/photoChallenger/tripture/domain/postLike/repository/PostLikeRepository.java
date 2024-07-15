package com.photoChallenger.tripture.domain.postLike.repository;

import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
