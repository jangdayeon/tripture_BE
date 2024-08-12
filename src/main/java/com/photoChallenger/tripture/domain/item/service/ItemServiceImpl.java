package com.photoChallenger.tripture.domain.item.service;

import com.photoChallenger.tripture.domain.item.dto.*;
import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.item.repository.ItemRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.global.elasticSearch.itemSearch.ItemDocument;
import com.photoChallenger.tripture.global.elasticSearch.itemSearch.ItemSearchService;
import com.photoChallenger.tripture.global.exception.item.NoSuchItemException;
import com.photoChallenger.tripture.global.exception.item.OutOfStockException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final LoginRepository loginRepository;
    private final ItemSearchService itemSearchService;
    /**
     * 상품 목록 조회
     */
    public GetItemAllResponse getItemList(int pageNo, String criteria) {
        Pageable pageable = PageRequest.of(pageNo,5, Sort.by(Sort.Direction.DESC, criteria));
        List<Item> orderByItemViewCount = itemRepository.findAll(pageable).getContent();
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

    /**
     * 상품 구매 시 버튼
     */
    public PriceCalculateResponse priceCalculate(PriceCalculateRequest priceCalculateRequest, Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Item item = itemRepository.findById(priceCalculateRequest.getItemId()).orElseThrow(NoSuchItemException::new);

        if(priceCalculateRequest.getItemCount() > item.getItemStock()) {
            throw new OutOfStockException();
        }

        return new PriceCalculateResponse(priceCalculateRequest.getItemCount() * item.getItemPrice()
                , login.getProfile().getProfileTotalPoint());

    }

    @Override
    public SearchListResponse searchItem(String searchOne, int pageNo, String properties) {
        Pageable pageable = PageRequest.of(pageNo,5, Sort.by(Sort.Direction.DESC, properties));
        List<ItemDocument> itemDocuments =  itemSearchService.getItemByItemName(searchOne);
        List<Long> itemIds = itemDocuments.stream()
                .map(o -> o.getItemId())
                .collect(Collectors.toList());
        Page<Item> page = itemRepository.findAllByItem_ItemId(itemIds, pageable);
        List<Item> itemList = page.getContent();
        List<SearchResponse> searchResponseList = itemList.stream()
                .map(o -> new SearchResponse(o.getItemId(),o.getItemImgName(),o.getItemName(),o.getItemPosition(),o.getItemPrice()))
                .collect(Collectors.toList());
        return new SearchListResponse(page.getTotalPages(), searchResponseList);
    }

}
