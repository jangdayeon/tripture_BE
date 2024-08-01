package com.photoChallenger.tripture.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PriceCalculateResponse {
    Integer itemTotalPrice;
    Integer havePoint;
}
