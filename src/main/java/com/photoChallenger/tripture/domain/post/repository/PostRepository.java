package com.photoChallenger.tripture.domain.post.repository;

import com.photoChallenger.tripture.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByProfile_ProfileId(Long profileId, Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "LEFT JOIN FETCH p.challenge ch " +
            "LEFT JOIN FETCH p.profile pr " +
            "LEFT JOIN FETCH p.profile.postCnt pc " +
            "WHERE p.postId = :postId")
    Post findPostFetchJoin(Long postId);

    @Query("SELECT p FROM Post p WHERE p.challenge.challengeId IN (:challengeIds)")
    Page<Post> findAllByChallenge_ChallengeId(List<Long> challengeIds, Pageable pageable);

}
