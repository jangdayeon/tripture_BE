package com.photoChallenger.tripture.domain.bookmark.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoChallenge extends Bookmark {

    private Long postId;

    private PhotoChallenge(Long postId) {
        this.postId = postId;
    }

    public static PhotoChallenge create(Profile profile, Long postId) {
        PhotoChallenge photoChallenge = new PhotoChallenge(postId);
        photoChallenge.addProfile(profile);
        return photoChallenge;
    }

}
