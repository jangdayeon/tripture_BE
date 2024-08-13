package com.photoChallenger.tripture.domain.item.controller;

import com.photoChallenger.tripture.domain.item.dto.GetItemAllResponse;
import com.photoChallenger.tripture.domain.item.dto.GetItemDetailResponse;
import com.photoChallenger.tripture.domain.item.dto.PriceCalculateRequest;
import com.photoChallenger.tripture.domain.item.dto.PriceCalculateResponse;
import com.photoChallenger.tripture.domain.item.service.ItemService;
import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.item.dto.SearchListResponse;
import com.photoChallenger.tripture.global.exception.InputFieldException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    public GetItemAllResponse getItemAllList(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                             @RequestParam(required = false, defaultValue = "itemDate", value = "criteria") String criteria){ //ItemDate or ItemViewCount
        return itemService.getItemList(pageNo, criteria);
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

    @GetMapping("/search")
    public ResponseEntity<SearchListResponse> searchPost(@RequestParam(required = true) String searchOne,
                                                         @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                        @RequestParam(required = false, defaultValue = "itemViewCount", value = "criteria") String properties) throws IOException {
        if(searchOne.isBlank()) {
            throw new InputFieldException("검색어는 필수입니다", HttpStatus.BAD_REQUEST,"post");
        }
        String searchDecoding = URLDecoder.decode(searchOne, "UTF-8").describeConstable().orElseThrow().toLowerCase(Locale.ROOT);
        return ResponseEntity.ok().body(itemService.searchItem(searchDecoding, pageNo, properties));
    }

}
