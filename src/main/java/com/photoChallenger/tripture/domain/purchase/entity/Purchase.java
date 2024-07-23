package com.photoChallenger.tripture.domain.purchase.entity;

import com.photoChallenger.tripture.domain.point.entity.Point;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.purchaseItem.entity.PurchaseItem;
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
    private String uid;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean check;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer purchaseCount;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer purchasePrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    private Purchase(String uid, Integer purchaseCount, Integer purchasePrice) {
        this.uid = uid;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.check = false;
    }

    @Builder
    public static Purchase create(Profile profile, String uid, Integer purchaseCount, Integer purchasePrice) {
        Purchase purchase = new Purchase(uid, purchaseCount, purchasePrice);
        purchase.addProfile(profile);
        return purchase;
    }

    private void addProfile(Profile profile) {
        this.profile = profile;
        profile.getPurchases().add(this);
    }

    public void remove(Profile profile) {
        profile.getPurchases().remove(this);
    }

}
