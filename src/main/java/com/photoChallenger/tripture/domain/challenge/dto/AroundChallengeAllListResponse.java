package com.photoChallenger.tripture.domain.challenge.dto;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AroundChallengeAllListResponse {
    List<AroundChallenge> aroundChallenges;

    public static AroundChallengeAllListResponse from(List<Challenge> challenges) {
        List<AroundChallenge> aroundChallengeList = challenges.stream()
                .map(AroundChallenge::from).toList();
        return new AroundChallengeAllListResponse(aroundChallengeList);

    }
}
