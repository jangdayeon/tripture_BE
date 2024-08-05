package com.photoChallenger.tripture.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoDto {
    private int price; //가격
    private int amount; //개수
    private long itemId; //상품Id
}
