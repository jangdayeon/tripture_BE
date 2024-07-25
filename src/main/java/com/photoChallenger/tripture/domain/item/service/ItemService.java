package com.photoChallenger.tripture.domain.item.service;

import com.photoChallenger.tripture.domain.item.dto.GetItemAllResponse;
import com.photoChallenger.tripture.domain.item.dto.GetItemDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ItemService {

    /**
     * 상품 목록 조회
     */
    GetItemAllResponse getItemList();

    /**
     * 상품 조회
     */
    GetItemDetailResponse getItemDetail(Long itemId);
}
