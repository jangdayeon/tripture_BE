package com.photoChallenger.tripture.domain.purchaseItem.entity;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PurchaseItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long purchaseItemId;

    private Integer purchaseCount;
    private Integer purchasePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private PurchaseItem(Integer purchaseCount, Integer purchasePrice) {
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
    }

    @Builder
    public static PurchaseItem create(Purchase purchase, Item item, Integer purchaseCount, Integer purchasePrice) {
        PurchaseItem purchaseItem = new PurchaseItem(purchaseCount, purchasePrice);
        purchaseItem.addPurchase(purchase);
        purchaseItem.addItem(item);
        return purchaseItem;
    }

    private void addPurchase(Purchase purchase) {
        this.purchase = purchase;
        purchase.getPurchaseItems().add(this);
    }

    private void addItem(Item item) {
        this.item = item;
        item.getPurchaseItems().add(this);
    }
}
