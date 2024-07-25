package com.photoChallenger.tripture.domain.bookmark.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends Bookmark {

    private String contentId;

    private Content(String contentId) {
        this.contentId = contentId;
    }

    @Builder
    public static Content create(Profile profile, String contentId) {
        Content content = new Content(contentId);
        content.addProfile(profile);
        content.setBookmarkTime(LocalDateTime.now());
        return content;
    }

}
