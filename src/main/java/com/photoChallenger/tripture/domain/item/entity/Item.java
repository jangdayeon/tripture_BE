package com.photoChallenger.tripture.domain.item.entity;

import com.photoChallenger.tripture.domain.purchaseItem.entity.PurchaseItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(length = 255)
    private String itemImgUrl;

    @Column(length = 255)
    private String itemImgName;

    @Column(columnDefinition = "longtext")
    private String itemDescription;

    @Column(nullable = false)
    private Integer itemPrice;

    @Column(length = 255, nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private ItemType itemType;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    @Builder
    private Item(String itemImgUrl, String itemImgName, String itemDescription, Integer itemPrice, String itemName, ItemType itemType) {
        this.itemImgUrl = itemImgUrl;
        this.itemImgName = itemImgName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public Item changeItem(Integer itemPrice, String itemImgName, String itemImgUrl, String itemDescription) {
        this.itemPrice = itemPrice;
        this.itemImgName = itemImgName;
        this.itemImgUrl = itemImgUrl;
        this.itemDescription = itemDescription;

        return this;
    }

}
