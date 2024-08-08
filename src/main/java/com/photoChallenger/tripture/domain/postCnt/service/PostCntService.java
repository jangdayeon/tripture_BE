package com.photoChallenger.tripture.domain.postCnt.service;

import com.photoChallenger.tripture.domain.postCnt.dto.PostChallengeCntResponse;

public interface PostCntService {
    PostChallengeCntResponse findProfile(Long loginId);
}