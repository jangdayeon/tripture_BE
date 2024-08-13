package com.photoChallenger.tripture.domain.bookmark.service;

import com.photoChallenger.tripture.domain.bookmark.dto.MyContentListResponse;
import com.photoChallenger.tripture.domain.bookmark.dto.MyContentResponse;
import com.photoChallenger.tripture.domain.bookmark.dto.MyPhotoChallengeListResponse;
import com.photoChallenger.tripture.domain.bookmark.dto.MyPhotoChallengeResponse;

import java.util.List;

public interface BookmarkService {

    //내가 저장한 관광지 리스트
    MyContentListResponse getContentList(Long loginId, int pageNo);

    //내가 저장한 챌린지 리스트
    MyPhotoChallengeListResponse getPhotoChallengeList(Long loginId, int pageNo);

    //북마크 저장
    String savePhotoChallengeBookmark(Long postId, Long loginId);
}
