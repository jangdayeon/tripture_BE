package com.photoChallenger.tripture.domain.challenge.dto;

import com.photoChallenger.tripture.domain.profile.entity.ProfileLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.photoChallenger.tripture.global.S3.S3Url.S3_URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPhotoChallengeResponse {
    Long challengeId;
    String challengeImgUrl;
    String challengeName;
    String challengeContent;
    Integer challengePoint;
    boolean isChallengeParticipate;

    @Builder
    public static GetPhotoChallengeResponse from(Long challengeId, String challengeImgName
            , String challengeName, String challengeContent
            , Integer challengePoint, boolean isChallengeParticipate) {
        return new GetPhotoChallengeResponse(challengeId, S3_URL + challengeImgName
                , challengeName, challengeContent, challengePoint, isChallengeParticipate);
    }



}
