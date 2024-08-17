package com.photoChallenger.tripture.domain.challenge.dto;

import com.photoChallenger.tripture.domain.challenge.entity.ChallengeRegion;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurroundingChallengeResponse {
    private Long challengeId;
    private String challengeImgName;
    private String challengeName;
    private ChallengeRegion challengeRegion;
    private Long distance_meter;
    private Long participants;

    public static SurroundingChallengeResponse of(ChallengeAppendDistanceDto challengeAppendDistanceDto, Long participants){
        return SurroundingChallengeResponse.builder()
                .challengeId(challengeAppendDistanceDto.getChallenge().getChallengeId())
                .challengeImgName(S3Url.S3_URL+challengeAppendDistanceDto.getChallenge().getChallengeImgName())
                .challengeName(challengeAppendDistanceDto.getChallenge().getChallengeName())
                .challengeRegion(challengeAppendDistanceDto.getChallenge().getChallengeRegion())
                .distance_meter(Math.round(challengeAppendDistanceDto.getDistance()))
                .participants(participants)
                .build();
    }
}