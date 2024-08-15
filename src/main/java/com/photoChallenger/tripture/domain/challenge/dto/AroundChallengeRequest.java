package com.photoChallenger.tripture.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AroundChallengeRequest {
    Double lat;
    Double lon;
}
