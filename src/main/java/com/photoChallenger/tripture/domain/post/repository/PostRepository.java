package com.photoChallenger.tripture.domain.post.repository;

import com.photoChallenger.tripture.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByProfile_ProfileId(Long profileId, Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "LEFT JOIN FETCH p.challenge ch " +
            "LEFT JOIN FETCH p.profile pr " +
            "LEFT JOIN FETCH p.profile.postCnt pc " +
            "WHERE p.postId = :postId")
    Post findPostFetchJoin(Long postId);

    Boolean existsByProfile_ProfileIdAndChallenge_ChallengeId(Long profileId, Long challengeId);
}
