package com.photoChallenger.tripture.domain.item.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                ,"${cloud.aws.url}" + item.getItemImgName()
                ,item.getItemPrice()
                ,item.getItemName()
                ,item.getItemPosition()
                ,item.getItemStock());
    }
}
