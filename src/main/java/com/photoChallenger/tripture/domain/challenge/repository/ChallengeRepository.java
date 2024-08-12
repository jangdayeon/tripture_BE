package com.photoChallenger.tripture.domain.challenge.repository;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Boolean existsByContentId(String contentId);
}
