package com.photoChallenger.tripture.domain.purchase.entity;

import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Builder;

import static jakarta.persistence.FetchType.*;

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
