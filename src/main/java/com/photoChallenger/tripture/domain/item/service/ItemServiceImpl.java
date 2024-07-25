package com.photoChallenger.tripture.domain.item.service;

import com.photoChallenger.tripture.domain.item.dto.GetItemAllResponse;
import com.photoChallenger.tripture.domain.item.dto.GetItemDetailResponse;
import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.item.repository.ItemRepository;
import com.photoChallenger.tripture.global.exception.item.NoSuchItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 목록 조회
     */
    public GetItemAllResponse getItemList() {
        List<Item> orderByItemViewCount = itemRepository.findByOrderByItemViewCountDesc();
        return GetItemAllResponse.from(orderByItemViewCount);
    }

    /**
     * 상품 조회
     */
    public GetItemDetailResponse getItemDetail(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchItemException::new);
        return GetItemDetailResponse.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemImgName(item.getItemImgName())
                .itemDescription(item.getItemDescription())
                .itemPrice(item.getItemPrice())
                .itemPosition(item.getItemPosition())
                .build();
    }

}
