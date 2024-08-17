package com.photoChallenger.tripture.domain.item.dto;

import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SearchResponse {
    Long itemId;
    String itemImgName;
    String itemName;
    String itemPosition;
    Integer itemPrice;

    public SearchResponse(Long itemId, String itemImgName, String itemName, String itemPosition, Integer itemPrice) {
        this.itemId = itemId;
        this.itemImgName = S3Url.S3_URL + itemImgName;
        this.itemName =  itemName;
        this.itemPosition = itemPosition;
        this.itemPrice = itemPrice;
    }
}
