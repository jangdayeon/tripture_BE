package com.photoChallenger.tripture.domain.item.controller;

import com.photoChallenger.tripture.domain.item.dto.GetItemAllResponse;
import com.photoChallenger.tripture.domain.item.dto.GetItemDetailResponse;
import com.photoChallenger.tripture.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
