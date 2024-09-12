package com.photoChallenger.tripture.domain.challenge.service;

import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;

public interface ChallengeService {

    boolean isPhotoChallengeExist(String contentId);

    GetPhotoChallengeResponse getPhotoChallenge(String contentId, Long loginId);

    GetPhotoChallengeResponse getPhotoChallenge(Long challengeId, Long loginId);

    //List<Challenge> getAroundChallengeList(double lat, double lon, double distance);

    //List<SurroundingChallengeResponse> getSurroundingChallengeList(Long loginId, double lat, double lon, double distance, String properties);
}
