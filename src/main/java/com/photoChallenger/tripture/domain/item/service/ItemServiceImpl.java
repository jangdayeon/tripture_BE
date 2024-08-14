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
import com.photoChallenger.tripture.global.exception.redis.AlreadyCheckUserException;
import com.photoChallenger.tripture.global.redis.RedisDao;
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
    private final RedisDao redisDao;
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
    public GetItemDetailResponse getItemDetail(Long itemId, Long loginId) {
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchItemException::new);
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);

        String redisKey = "item:view:" + item.getItemId().toString(); // 조회수 key
        String redisUserKey = "user:item:" + login.getLoginId().toString(); // 유저 key

        int views = 0;
        if(redisDao.getValues(redisKey) == null) {
            views = item.getItemViewCount().intValue();
        } else {
            views = Integer.parseInt(redisDao.getValues(redisKey));
        }

        // 유저를 key로 조회한 게시글 ID List안에 해당 게시글 ID가 포함되어있지 않는다면,
        if (!redisDao.getValuesList(redisUserKey).contains(redisKey)) {
            redisDao.setValuesList(redisUserKey, redisKey); // 유저 key로 해당 글 ID를 List 형태로 저장
            views = views + 1; // 조회수 증가
            redisDao.setValues(redisKey, String.valueOf(views)); // 글ID key로 조회수 저장
            redisDao.expireValues(redisKey, 60 * 24);
            redisDao.expireValues(redisUserKey, 10);
        }

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
