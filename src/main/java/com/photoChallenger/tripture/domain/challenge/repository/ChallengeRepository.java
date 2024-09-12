package com.photoChallenger.tripture.domain.challenge.repository;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Boolean existsByContentId(String contentId);

    Optional<Challenge> findByContentId(String contentId);

    /*@Query("SELECT c FROM Challenge c WHERE c.challengeLatitude BETWEEN :minY AND :maxY AND c.challengeLongitude BETWEEN :minX AND :maxX")
    List<Challenge> getAroundChallengeList(@Param("maxY") double maxY, @Param("maxX") double maxX,
                                      @Param("minY") double minY, @Param("minX") double minX);*/
}
