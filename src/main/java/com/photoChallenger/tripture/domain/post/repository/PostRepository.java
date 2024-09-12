package com.photoChallenger.tripture.domain.post.repository;

import com.photoChallenger.tripture.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByProfile_ProfileId(Long profileId, Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "LEFT JOIN FETCH p.profile pr " +
            "LEFT JOIN FETCH p.profile.postCnt pc " +
            "WHERE p.postId = :postId")
    Post findPostFetchJoin(Long postId);

    Boolean existsByProfile_ProfileIdAndContentId(Long profileId, String contentId);

    @Query("SELECT p FROM Post p WHERE p.postId IN (:postIds)")
    Page<Post> findAllByPost_PostId(List<Long> postIds, Pageable pageable);

    @Query("select p from Post p order by p.postLikeCount*0.4+p.postViewCount*0.6")
    Page<Post> findPopularPost(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postId IN (:postIds)")
    List<Post> findAllByPost_PostIds(List<Long> postIds);

    @Query("select p from Post p order by :properties limit 10")
    List<Post> findPopularPostList(String properties);
}
