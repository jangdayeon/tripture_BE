package com.photoChallenger.tripture.domain.profile.repository;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Boolean existsByProfileNickname(String nickname);

    @Query("select p " +
            "from Profile p " +
            "left join fetch p.postCnt " +
            "left join fetch p.bookmarks " +
            "left join fetch p.login " +
            "left join fetch p.points " +
            "left join fetch p.purchases " +
            "left join fetch p.post " +
            "where p.profileId = :profileId")
    Profile findAllByProfileId(Long profileId);
}
