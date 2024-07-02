package com.photoChallenger.tripture.domain.bookmark.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.print.Book;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Bookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BookmarkId;

    @Column(length = 255)
    private String ContentId;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private Bookmark(Long bookmarkId, String contentId) {
        this.BookmarkId = bookmarkId;
        this.ContentId = contentId;
    }

    @Builder
    public static Bookmark create(Profile profile, Long bookmarkId, String contentId) {
        Bookmark bookmark = new Bookmark(bookmarkId, contentId);
        bookmark.addProfile(profile);
        return bookmark;
    }

    private void addProfile(Profile profile) {
        this.profile = profile;
        profile.getBookmarks().add(this);
    }
}
