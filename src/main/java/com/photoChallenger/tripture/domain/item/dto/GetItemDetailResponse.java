package com.photoChallenger.tripture.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetItemDetailResponse {
    Long itemId;
    String itemName;
    String itemDescription;
    String itemImgName;
    Integer itemPrice;
    String itemPosition;

    @Value("${cloud.aws.url}")
    static String domain;
    @Builder
    public static GetItemDetailResponse of(Long itemId, String itemName, String itemDescription, String itemImgName, Integer itemPrice, String itemPosition) {
        return new GetItemDetailResponse(itemId, itemName, itemDescription, domain + itemImgName, itemPrice, itemPosition);
    }

}
