package com.photoChallenger.tripture.domain.bookmark.repository;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    //내가 저장한 관광지 or 챌린지에서 최신순으로 리스트 불러올 때 쓰임
    @Query(value = "SELECT b FROM Bookmark b WHERE b.profile.profileId = :profile_id AND type(b) = :bookmark_type order by b.bookmarkTime DESC ")
    List<Bookmark> findAllByProfile_ProfileIdAndTypeOOrderByBookmarkTimeDESC(@Param("profile_id") Long profileId, @Param("bookmark_type") Class<? extends Bookmark> type, Pageable pageable);
}
