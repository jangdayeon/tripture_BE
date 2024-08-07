package com.photoChallenger.tripture.domain.bookmark.service;

import com.photoChallenger.tripture.domain.bookmark.dto.MyContentResponse;
import com.photoChallenger.tripture.domain.bookmark.dto.MyPhotoChallengeResponse;

import java.util.List;

public interface BookmarkService {

    //내가 저장한 관광지 리스트
    public List<MyContentResponse> getContentList(Long loginId, int pageNo);

    //내가 저장한 챌린지 리스트
    public List<MyPhotoChallengeResponse> getPhotoChallengeList(Long loginId, int pageNo);
}
