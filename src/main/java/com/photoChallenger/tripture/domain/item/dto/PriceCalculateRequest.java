package com.photoChallenger.tripture.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculateRequest {
    Long itemId;
    Integer itemCount;
}
