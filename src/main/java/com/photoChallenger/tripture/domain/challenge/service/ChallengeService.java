package com.photoChallenger.tripture.domain.challenge.service;

import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.entity.Challenge;

import java.util.List;
import com.photoChallenger.tripture.domain.challenge.dto.SurroundingChallengeResponse;

import java.util.List;

public interface ChallengeService {

    boolean isPhotoChallengeExist(String contentId);

    GetPhotoChallengeResponse getPhotoChallenge(String contentId, Long loginId);

    List<Challenge> getAroundChallengeList(double lat, double lon, double distance);

    List<SurroundingChallengeResponse> getSurroundingChallengeList(Long loginId, double lat, double lon, double distance, String properties);
}
