package com.photoChallenger.tripture.domain.profile.repository;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Boolean existsByProfileNickname(String nickname);
}
