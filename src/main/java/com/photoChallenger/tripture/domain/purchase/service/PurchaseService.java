package com.photoChallenger.tripture.domain.purchase.service;

import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemDto;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemResponse;

import java.util.List;

public interface PurchaseService {
    //사용 전 아이템 리스트
    public List<PurchaseItemResponse> checkItemsBeforeUse(long loginId);

    //사용 후 아이템 리스트
    public List<PurchaseItemResponse> checkItemsAfterUse(long loginId);

    //아이템 상세정보 및 구매정보 확인
    public PurchaseItemDto checkDetail(long purchaseId);

    //아이템 사용하기
    public void useItem(long purchaseId);
}
