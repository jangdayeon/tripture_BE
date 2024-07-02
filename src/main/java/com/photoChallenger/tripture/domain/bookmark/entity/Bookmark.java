package com.photoChallenger.tripture.domain.bookmark.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
