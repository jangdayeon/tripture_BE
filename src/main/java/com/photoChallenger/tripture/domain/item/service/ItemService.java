package com.photoChallenger.tripture.domain.item.service;

import com.photoChallenger.tripture.domain.item.dto.GetItemAllResponse;
import com.photoChallenger.tripture.domain.item.dto.GetItemDetailResponse;
import com.photoChallenger.tripture.domain.item.dto.PriceCalculateRequest;
import com.photoChallenger.tripture.domain.item.dto.PriceCalculateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ItemService {

    /**
     * 상품 목록 조회
     */
    GetItemAllResponse getItemList(int pageNo, String criteria);

    /**
     * 상품 조회
     */
    GetItemDetailResponse getItemDetail(Long itemId, Long loginId);

    /**
     * 상품 구매 시 버튼
     */
    PriceCalculateResponse priceCalculate(PriceCalculateRequest priceCalculateRequest, Long loginId);
}
