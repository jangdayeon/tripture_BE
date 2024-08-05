package com.photoChallenger.tripture.domain.purchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.item.repository.ItemRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.domain.purchase.dto.KakaoPayResponse;
import com.photoChallenger.tripture.domain.purchase.dto.PayInfoDto;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemDto;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemResponse;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import com.photoChallenger.tripture.domain.purchase.repository.PurchaseRepository;
import com.photoChallenger.tripture.global.exception.item.NoSuchItemException;
import com.photoChallenger.tripture.global.exception.purchase.AlreadyUsedItemException;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final LoginRepository loginRepository;
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    @Value("${ADMIN_KEY}")
    String admin_Key; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요

    public List<PurchaseItemResponse> checkItemsBeforeUse(long loginId) {
        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();
        List<Purchase> purchaseList = profile.getPurchases();
        List<PurchaseItemResponse> purchaseItemResponseList = new ArrayList<>();
        for(Purchase p : purchaseList){
            if( p.getPurchaseCheck() == false){ //사용 전
                purchaseItemResponseList.add(PurchaseItemResponse.from(p, p.getItem()));
            }
        }
        return purchaseItemResponseList;
    }

    @Override
    public List<PurchaseItemResponse> checkItemsAfterUse(long loginId) {
        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();
        List<Purchase> purchaseList = profile.getPurchases();
        List<PurchaseItemResponse> purchaseItemResponseList = new ArrayList<>();
        for(Purchase p : purchaseList){
            if( p.getPurchaseCheck() == true){ //사용 후
                purchaseItemResponseList.add(PurchaseItemResponse.from(p, p.getItem()));
            }
        }
        return purchaseItemResponseList;
    }

    @Override
    public PurchaseItemDto checkDetail(long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        return PurchaseItemDto.from(purchase.getItem(), purchase);
    }

    @Override
    @Transactional
    public void useItem(long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        if(purchase.getPurchaseCheck() == true){
            throw new AlreadyUsedItemException();
        }
            purchase.update(true);
    }

    @Override
    public KakaoPayResponse kakaoPayReady(PayInfoDto payInfoDto) throws JsonProcessingException {
        String order_id = UUID.randomUUID().toString();
        Item item = itemRepository.findById(payInfoDto.getItemId()).orElseThrow(NoSuchItemException::new);
        log.info("제발",item==null);
        // 카카오페이 요청 양식
        Map<String, String> parameters = new ConcurrentHashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", order_id); //가맹점 주문 번호
        parameters.put("partner_user_id", "tripture");//가맹점 회원 ID
        parameters.put("item_name", item.getItemName());
        parameters.put("quantity", ""+payInfoDto.getAmount());
        parameters.put("total_amount", ""+payInfoDto.getPrice());
        parameters.put("tax_free_amount", ""+(payInfoDto.getPrice() - (int) payInfoDto.getPrice()/10));
        parameters.put("approval_url", "http://www.tripture.shop/payment/success"); // 성공 시 redirect url
        parameters.put("cancel_url", "http://www.tripture.shop/payment/cancel"); // 취소 시 redirect url
        parameters.put("fail_url", "http://www.tripture.shop/payment/fail"); // 실패 시 redirect url
        log.info("돼!!!!!!!!!!!!!!!!!");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(parameters);

        // 파라미터, 헤더
        HttpEntity<String> requestEntity = new HttpEntity<>(json, this.getHeaders());
        log.info(requestEntity.toString());
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayResponse kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoPayResponse.class);
        log.info("여기!!!!!!!!!"+kakaoReady.toString());

        return kakaoReady;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = admin_Key;

        httpHeaders.set("Authorization", "SECRET_KEY "+auth);
        httpHeaders.set("Content-type", "application/json");

//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setBearerAuth(auth);

        return httpHeaders;
    }
}
