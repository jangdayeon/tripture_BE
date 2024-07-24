package com.photoChallenger.tripture.domain.purchase.controller;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemDto;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemResponse;
import com.photoChallenger.tripture.domain.purchase.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    //사용 전 아이템 리스트
    @GetMapping("/ItemsBeforeUse")
    public ResponseEntity<List<PurchaseItemResponse>> checkItemsBeforeUse(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(purchaseService.checkItemsBeforeUse(loginIdResponse.getLoginId()));
    }
    //사용 후 아이템 리스트
    @GetMapping("/ItemsAfterUse")
    public ResponseEntity<List<PurchaseItemResponse>> checkItemsAfterUse(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(purchaseService.checkItemsAfterUse(loginIdResponse.getLoginId()));
    }

    //아이템 상세정보 및 구매정보 확인
    @GetMapping("/{purchaseId}/detail")
    public ResponseEntity<PurchaseItemDto> checkDetail(HttpServletRequest request, @PathVariable("purchaseId") Long purchaseId){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok().body(purchaseService.checkDetail(purchaseId));
    }

    //아이템 사용하기
    @PostMapping("/{purchaseId}/use")
    public ResponseEntity<String> useTicket(HttpServletRequest request, @PathVariable("purchaseId") Long purchaseId){
        HttpSession session = request.getSession(false);
        LoginIdResponse loginIdResponse = (LoginIdResponse) session.getAttribute(SessionConst.LOGIN_MEMBER);
        purchaseService.useItem(purchaseId);
        return new ResponseEntity("redirection request", HttpStatus.SEE_OTHER);
    }

}
