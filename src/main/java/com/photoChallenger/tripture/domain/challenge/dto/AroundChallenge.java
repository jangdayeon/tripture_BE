package com.photoChallenger.tripture.domain.challenge.dto;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.challenge.entity.ChallengeRegion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.photoChallenger.tripture.global.S3.S3Url.S3_URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AroundChallenge {
    Long challengeId;
    String contentId;
    String challengeName;
    String challengeImgUrl;
    String challengeContent;
    int challengePoint;
    ChallengeRegion challengeRegion;
    double challengeLat;
    double challengeLon;

    public static AroundChallenge from(Challenge challenge) {
        return new AroundChallenge(challenge.getChallengeId(), challenge.getContentId(), challenge.getChallengeName(), S3_URL + challenge.getChallengeImgName(),
                challenge.getChallengeContent(), challenge.getChallengePoint(), challenge.getChallengeRegion(),
                challenge.getChallengeLatitude(), challenge.getChallengeLongitude());
    }
}
