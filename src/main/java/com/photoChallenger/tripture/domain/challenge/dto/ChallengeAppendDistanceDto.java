package com.photoChallenger.tripture.domain.challenge.dto;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ChallengeAppendDistanceDto {
    Challenge challenge;
    double distance;
}
