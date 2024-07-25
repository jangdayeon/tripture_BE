package com.photoChallenger.tripture.domain.purchase.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemDto {
    private String itemImgName;
    private String itemName;
    private String itemPosition;
    private Integer purchaseCount;
    private Boolean purchaseCheck;

    public static PurchaseItemDto from(Item item, Purchase purchase){
        return new PurchaseItemDto("${cloud.aws.url}"+item.getItemImgName(), item.getItemName(), item.getItemPosition(), purchase.getPurchaseCount(), purchase.getPurchaseCheck());
    }
}
