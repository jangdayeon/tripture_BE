package com.photoChallenger.tripture.domain.purchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.photoChallenger.tripture.domain.purchase.dto.*;

import java.util.List;

public interface PurchaseService {
    //사용 전 아이템 리스트
    List<PurchaseItemResponse> checkItemsBeforeUse(long loginId);

    //사용 후 아이템 리스트
    List<PurchaseItemResponse> checkItemsAfterUse(long loginId);

    //아이템 상세정보 및 구매정보 확인
    PurchaseItemDto checkDetail(long purchaseId);

    //아이템 사용하기
    void useItem(long purchaseId);

    //카카오 페이 결제
    KakaoPayResponse kakaoPayReady(PayInfoDto payInfoDto, Long loginId) throws JsonProcessingException;

    ApproveResponse payApprove(KakaoPaySessionDto kakaoPaySessionDto, String pgToken);

}
