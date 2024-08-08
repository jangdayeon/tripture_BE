package com.photoChallenger.tripture.domain.purchase.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
        return new PurchaseItemDto(S3Url.S3_URL+item.getItemImgName(), item.getItemName(), item.getItemPosition(), purchase.getPurchaseCount(), purchase.getPurchaseCheck());
    }
}
