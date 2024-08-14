package com.photoChallenger.tripture.domain.item.entity;

import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
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

    @Column
    private String itemPosition;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    @Builder
    private Item(String itemImgName, String itemDescription, Integer itemPrice, String itemName, ItemType itemType, Integer itemStock, LocalDateTime itemDate, String itemPosition) {
        this.itemImgName = itemImgName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemStock = itemStock;
        this.itemDate = itemDate;
        this.itemViewCount = 0L;
        this.itemPosition = itemPosition;
    }

    public void viewCountRedis(Long view) {
        this.itemViewCount = view;
    }

    public Item changeItem(Integer itemPrice, String itemImgName, String itemDescription, Integer itemStock, Long itemViewCount, String itemPosition) {
        this.itemPrice = itemPrice;
        this.itemImgName = itemImgName;
        this.itemDescription = itemDescription;
        this.itemStock = itemStock;
        this.itemViewCount = itemViewCount;
        this.itemPosition = itemPosition;
        return this;
    }

    public void itemStockSubtract(Integer quantity) {
        this.itemStock -= quantity;
    }

}
