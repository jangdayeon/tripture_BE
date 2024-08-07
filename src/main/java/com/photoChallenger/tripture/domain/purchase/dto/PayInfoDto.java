package com.photoChallenger.tripture.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoDto {
    private Integer price; //가격
    private Integer amount; //개수
    private Long itemId; //상품Id
    private Integer usePoint; //포인트
}
