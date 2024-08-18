package com.photoChallenger.tripture.domain.purchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.item.repository.ItemRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.point.entity.Point;
import com.photoChallenger.tripture.domain.point.repository.PointRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.purchase.dto.*;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import com.photoChallenger.tripture.domain.purchase.entity.SessionUtils;
import com.photoChallenger.tripture.domain.purchase.repository.PurchaseRepository;
import com.photoChallenger.tripture.global.exception.item.NoSuchItemException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.point.LackPointException;
import com.photoChallenger.tripture.global.exception.purchase.AlreadyUsedItemException;
import com.photoChallenger.tripture.global.exception.purchase.NoSuchKakaoPayResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final LoginRepository loginRepository;
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;
    private final PointRepository pointRepository;

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    @Value("${ADMIN_KEY}")
    String admin_Key; // 애플리케이션의 어드민 키

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
        if(purchase.getPurchaseCheck()){
            throw new AlreadyUsedItemException();
        }
            purchase.update(true);
    }

    @Override
    @Transactional
    public KakaoPayResponse kakaoPayReady(PayInfoDto payInfoDto, Long loginId) throws JsonProcessingException {
        String order_id = UUID.randomUUID().toString();
        Profile profile = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new).getProfile();
        Item item = itemRepository.findById(payInfoDto.getItemId()).orElseThrow(NoSuchItemException::new);

        // 요청 json 메서드
        String jsonOrder = getOrderJson(payInfoDto, order_id, item);

        // 파라미터, 헤더
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonOrder, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayResponse kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoPayResponse.class);

        if(kakaoReady == null) {
            throw new NoSuchKakaoPayResponseException();
        }


        if(profile.getProfileTotalPoint() < payInfoDto.getUsePoint()) {
            throw new LackPointException();
        }


        SessionUtils.addAttribute("kakaoPaySession", new KakaoPaySessionDto(kakaoReady.getTid(),order_id,item.getItemId())); //세션에 tid 저장
        return kakaoReady;
    }

    @Override
    @Transactional
    public ApproveResponse payApprove(Long loginId, KakaoPaySessionDto kakaoPaySessionDto, String pgToken) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");              // 가맹점 코드(테스트용)
        parameters.put("tid", kakaoPaySessionDto.getTid());                       // 결제 고유번호
        parameters.put("partner_order_id", kakaoPaySessionDto.getOrder_id()); // 주문번호
        parameters.put("partner_user_id", "tripture");    // 회원 아이디
        parameters.put("pg_token", pgToken);              // 결제승인 요청을 인증하는 토큰

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        ApproveResponse approveResponse = template.postForObject(url, requestEntity, ApproveResponse.class);


        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();

        Item item = itemRepository.findById(kakaoPaySessionDto.getItem_id()).get();
        Integer usedPoint = ((item.getItemPrice()*approveResponse.getQuantity())-approveResponse.getAmount().getTotal());
        Purchase purchase = Purchase.builder()
                .tid(kakaoPaySessionDto.getTid())
                .purchaseCount(approveResponse.getQuantity())
                .purchasePrice(approveResponse.getAmount().getTotal())
                .item(item)
                .profile(profile).build();

        if(usedPoint > 0) {
            LocalDate now = LocalDate.now();

            Point point = Point.builder()
                    .profile(profile)
                    .pointTitle(item.getItemName())
                    .pointChange("-" + usedPoint)
                    .pointDate(now).build();

            pointRepository.save(point);
            profile.update(profile.getProfileTotalPoint() - usedPoint);
        }

        item.itemStockSubtract(approveResponse.getQuantity());
        purchaseRepository.save(purchase);


        return approveResponse;
    }

    private static String getOrderJson(PayInfoDto payInfoDto, String order_id, Item item) throws JsonProcessingException {
        // 카카오페이 요청 양식
        Map<String, String> parameters = new ConcurrentHashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", order_id); //가맹점 주문 번호
        parameters.put("partner_user_id", "tripture");//가맹점 회원 ID
        parameters.put("item_name", item.getItemName());
        parameters.put("quantity", ""+ payInfoDto.getAmount());
        parameters.put("total_amount", ""+((payInfoDto.getPrice()* payInfoDto.getAmount()) - payInfoDto.getUsePoint()));
        parameters.put("tax_free_amount", ""+0);
        parameters.put("approval_url", "http://localhost:8080/purchase/payment/success"); // 성공 시 redirect url
        parameters.put("cancel_url", "http://localhost:8080/purchase/payment/cancel"); // 취소 시 redirect url
        parameters.put("fail_url", "http://localhost:8080/purchase/payment/fail"); // 실패 시 redirect url

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(parameters);
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = admin_Key;

        httpHeaders.set("Authorization", "SECRET_KEY "+auth);
        httpHeaders.set("Content-type", "application/json");

        return httpHeaders;
    }
}
