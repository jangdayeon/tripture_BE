package com.photoChallenger.tripture.domain.purchase.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemResponse {
    Long purchaseId;
    String itemName;
    String itemPosition;

    public static PurchaseItemResponse from(Purchase purchase, Item item){
        return new PurchaseItemResponse(purchase.getPurchaseId(), item.getItemName(), item.getItemPosition());
    }
}
