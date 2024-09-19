package com.photoChallenger.tripture.domain.bookmark.repository;

import com.photoChallenger.tripture.domain.bookmark.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContentRepository extends JpaRepository<Content, Long> {
    boolean existsByProfile_ProfileIdAndContentId(Long profileId, String contentId);
}
