package com.photoChallenger.tripture.domain.bookmark.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="bookmarkType")
public abstract class Bookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long BookmarkId;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private LocalDateTime bookmarkTime;

    protected void addProfile(Profile profile) {
        this.profile = profile;
        profile.getBookmarks().add(this);
    }

    protected void remove(Profile profile) {
        profile.getBookmarks().remove(this);
    }

    protected void setBookmarkTime(LocalDateTime bookmarkTime) {
        this.bookmarkTime = bookmarkTime;
    }
}
