package com.photoChallenger.tripture.domain.bookmark.service;

import com.photoChallenger.tripture.domain.bookmark.dto.*;

import java.util.List;

public interface BookmarkService {

    //내가 저장한 관광지 리스트
    MyContentListResponse getContentList(Long loginId, int pageNo);

    //북마크 저장한 관광지 한 번에 조회
    GetContentListResponse getContentList(Long loginId);

    //내가 저장한 챌린지 리스트
    MyPhotoChallengeListResponse getPhotoChallengeList(Long loginId, int pageNo);

    //북마크 저장 (포토챌린지)
    BookmarkSaveResponse savePhotoChallengeBookmark(Long postId, Long loginId);

    //북마크 저장 (관광지)
    String saveContentIdBookmark(String contentId, Long loginId);
}
