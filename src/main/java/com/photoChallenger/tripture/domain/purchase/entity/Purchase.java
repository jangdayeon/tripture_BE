package com.photoChallenger.tripture.domain.purchase.entity;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Purchase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long purchaseId;

    @Column(nullable = false, length = 255)
    private String tid;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean purchaseCheck;

    @Column(columnDefinition = "INT")
    private Integer purchaseCount;

    @Column(columnDefinition = "INT")
    private Integer purchasePrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Purchase(String tid, Integer purchaseCount, Integer purchasePrice) {
        this.tid = tid;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.purchaseCheck = false;
    }

    @Builder
    public static Purchase create(Profile profile, Item item, String tid, Integer purchaseCount, Integer purchasePrice) {
        Purchase purchase = new Purchase(tid, purchaseCount, purchasePrice);
        purchase.addProfile(profile);
        purchase.addItem(item);
        return purchase;
    }

    public void update(Integer purchaseCount, Integer purchasePrice) {
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
    }

    public void update(Boolean check) {
        this.purchaseCheck = check;
    }

    private void addProfile(Profile profile) {
        this.profile = profile;
        profile.getPurchases().add(this);
    }

    private void addItem(Item item) {
        this.item = item;
        item.getPurchases().add(this);
    }

    public void remove(Profile profile, Item item) {
        profile.getPurchases().remove(this);
        item.getPurchases().remove(this);
    }

}
