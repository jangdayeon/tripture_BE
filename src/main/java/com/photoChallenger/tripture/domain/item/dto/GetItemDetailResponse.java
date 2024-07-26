package com.photoChallenger.tripture.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public static GetItemDetailResponse of(Long itemId, String itemName, String itemDescription, String itemImgName, Integer itemPrice, String itemPosition) {
        return new GetItemDetailResponse(itemId, itemName, itemDescription, "https://tripture.s3.ap-northeast-2.amazonaws.com/" + itemImgName, itemPrice, itemPosition);
    }

}
