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
    private Long purchaseId;
    @Column(length = 255)
    private String QRImgUrl;
    @Column(length = 255)
    private String QRImgName;
    @Column(length = 255)
    private String uid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    private Purchase(String qrImgUrl, String qrImgName, String uid) {
        this.QRImgUrl = qrImgUrl;
        this.QRImgName = qrImgName;
        this.uid = uid;
    }

    @Builder
    public static Purchase create(Profile profile, String qrImgUrl, String qrImgName, String uid) {
        Purchase purchase = new Purchase(qrImgUrl, qrImgName, uid);
        purchase.addProfile(profile);
        return purchase;
    }

    private void addProfile(Profile profile) {
        this.profile = profile;
        profile.getPurchases().add(this);
    }

}
