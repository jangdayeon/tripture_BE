package com.photoChallenger.tripture.domain.purchase.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
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

    @Value("${cloud.aws.url}")
    static String domain;

    public static PurchaseItemDto from(Item item, Purchase purchase){
        return new PurchaseItemDto(domain+item.getItemImgName(), item.getItemName(), item.getItemPosition(), purchase.getPurchaseCount(), purchase.getPurchaseCheck());
    }
}
