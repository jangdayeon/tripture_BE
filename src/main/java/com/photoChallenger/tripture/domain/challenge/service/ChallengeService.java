package com.photoChallenger.tripture.domain.challenge.service;

import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;

public interface ChallengeService {

    boolean isPhotoChallengeExist(String contentId);

    GetPhotoChallengeResponse getPhotoChallenge(String contentId, Long loginId);
}
