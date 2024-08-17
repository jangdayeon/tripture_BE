package com.photoChallenger.tripture.domain.challenge.dto;

import com.photoChallenger.tripture.domain.challenge.entity.ChallengeRegion;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.*;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurroundingChallengeResponse {
    private Long challengeId;
    private String challengeImgName;
    private String challengePoint;
    private String challengeRegion;
    private Long distance_meter;
    private Long participants;

    public static SurroundingChallengeResponse of(ChallengeAppendDistanceDto challengeAppendDistanceDto, Long participants){
        String[] challengeName = challengeAppendDistanceDto.getChallenge().getChallengeName().split(" ");
        String region = challengeName[0]; //챌린지명에서 챌린지 장소 파싱
        String point = String.join(" ",Arrays.copyOfRange(challengeName,1,challengeName.length));
        return SurroundingChallengeResponse.builder()
                .challengeId(challengeAppendDistanceDto.getChallenge().getChallengeId())
                .challengeImgName(S3Url.S3_URL+challengeAppendDistanceDto.getChallenge().getChallengeImgName())
                .challengePoint(point)
                .challengeRegion(region)
                .distance_meter(Math.round(challengeAppendDistanceDto.getDistance()))
                .participants(participants)
                .build();
    }
}