package com.photoChallenger.tripture.domain.item.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetItemResponse {
    private Long itemId;
    private String itemImgName;
    private Integer itemPrice;
    private String itemName;
    private String itemPosition;
    private Integer itemStock;

    @Builder
    public static GetItemResponse from(Item item) {
        return new GetItemResponse(item.getItemId()
                , S3Url.S3_URL + item.getItemImgName()
                ,item.getItemPrice()
                ,item.getItemName()
                ,item.getItemPosition()
                ,item.getItemStock());
    }
}
