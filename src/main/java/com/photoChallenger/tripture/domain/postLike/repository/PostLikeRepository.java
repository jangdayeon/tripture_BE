package com.photoChallenger.tripture.domain.postLike.repository;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("SELECT pl FROM PostLike pl WHERE pl.profileId = :profileId AND pl.post.postId = :postId")
    Optional<PostLike> findPostLikeByProfileIdAndPostId(@Param("profileId") Long profileId, @Param("postId") Long postId);

    @Modifying
    void deleteByProfileId(Long profileId);

    @Query("select pl " +
            "from PostLike pl " +
            "left join fetch pl.post p " +
            "where pl.profileId = :profileId")
    List<PostLike> findWhereILike(Long profileId);

}
