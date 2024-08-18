package com.photoChallenger.tripture.domain.item.dto;

import com.photoChallenger.tripture.global.S3.S3Url;
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

    @Builder
    public static GetItemDetailResponse of(Long itemId, String itemName, String itemDescription, String itemImgName, Integer itemPrice, String itemPosition) {
        return new GetItemDetailResponse(itemId, itemName, itemDescription, S3Url.S3_URL + itemImgName, itemPrice, itemPosition);
    }

}
