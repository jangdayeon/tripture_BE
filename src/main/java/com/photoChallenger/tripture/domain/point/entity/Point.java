package com.photoChallenger.tripture.domain.point.entity;

import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(length = 255)
    private String pointTitle;

    @Column(length = 255, nullable = false)
    private LocalDateTime pointDate;

    @Column(nullable = false)
    private Integer pointChange;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private Point(String pointTitle, LocalDateTime pointDate, Integer pointChange) {
        this.pointTitle = pointTitle;
        this.pointDate = pointDate;
        this.pointChange = pointChange;
    }

    @Builder
    public static Point create(Purchase purchase, String pointTitle, LocalDateTime pointDate, Integer pointChange) {
        Point point = new Point(pointTitle, pointDate, pointChange);
        point.addPurchase(purchase);
        return point;
    }

    private void addPurchase(Purchase purchase) {
        this.purchase = purchase;
        purchase.getPoints().add(this);
    }
}
