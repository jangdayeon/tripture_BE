package com.photoChallenger.tripture.domain.item.controller;

import com.photoChallenger.tripture.domain.item.dto.GetItemAllResponse;
import com.photoChallenger.tripture.domain.item.dto.GetItemDetailResponse;
import com.photoChallenger.tripture.domain.item.dto.PriceCalculateRequest;
import com.photoChallenger.tripture.domain.item.dto.PriceCalculateResponse;
import com.photoChallenger.tripture.domain.item.service.ItemService;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    public GetItemAllResponse getItemAllList() {
        return itemService.getItemList();
    }

    @GetMapping("/detail/{itemId}")
    public GetItemDetailResponse getItemDetail(@PathVariable("itemId") Long itemId) {
        return itemService.getItemDetail(itemId);
    }

    @GetMapping("/buy")
    public ResponseEntity<PriceCalculateResponse> priceCalculate(@RequestBody PriceCalculateRequest priceCalculateRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return ResponseEntity.ok().body(itemService.priceCalculate(priceCalculateRequest, loginIdResponse.getLoginId()));
    }
}
