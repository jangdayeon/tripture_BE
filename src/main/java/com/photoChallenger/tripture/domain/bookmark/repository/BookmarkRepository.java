package com.photoChallenger.tripture.domain.bookmark.repository;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    //내가 저장한 관광지 or 챌린지에서 최신순으로 리스트 불러올 때 쓰임
    @Query(value = "SELECT b FROM Bookmark b WHERE b.profile.profileId = :profile_id AND type(b) = :bookmark_type")
    Page<Bookmark> findAllByProfile_ProfileIdAndType(@Param("profile_id") Long profileId, @Param("bookmark_type") Class<? extends Bookmark> type, Pageable pageable);

    @Query("SELECT b FROM Bookmark b WHERE b.postId = :postId AND b.profile.profileId = :profileId")
    Optional<Bookmark> findBookmarkPostIdAndProfileId(@Param("postId") Long postId, @Param("profileId") Long profileId);

    @Query("SELECT b FROM Bookmark b WHERE b.contentId = :contentId AND b.profile.profileId = :profileId")
    Optional<Bookmark> findBookmarkContentIdAndProfileId(@Param("contentId") String contentId, @Param("profileId") Long profileId);

    @Query("SELECT b FROM Bookmark b WHERE b.profile.profileId = :profileId AND b.postId = :postId")
    Optional<Bookmark> existsByProfileIdAndPostId(@Param("profileId") Long profileId, @Param("postId") Long postId);

    @Modifying
    @Query("DELETE FROM Bookmark b WHERE b.postId = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
