package com.photoChallenger.tripture.domain.item.entity;

import com.photoChallenger.tripture.domain.purchaseItem.entity.PurchaseItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
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

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer itemStock;

    @Column(nullable = false)
    private LocalDateTime itemDate;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long itemViewCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    @Builder
    private Item(String itemImgUrl, String itemImgName, String itemDescription, Integer itemPrice, String itemName, ItemType itemType, Integer itemStock, LocalDateTime itemDate) {
        this.itemImgUrl = itemImgUrl;
        this.itemImgName = itemImgName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemStock = itemStock;
        this.itemDate = itemDate;
        this.itemViewCount = 0L;
    }

    public Item changeItem(Integer itemPrice, String itemImgName, String itemImgUrl, String itemDescription, Integer itemStock, Long itemViewCount) {
        this.itemPrice = itemPrice;
        this.itemImgName = itemImgName;
        this.itemImgUrl = itemImgUrl;
        this.itemDescription = itemDescription;
        this.itemStock = itemStock;
        this.itemViewCount = itemViewCount;

        return this;
    }

}
