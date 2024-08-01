package com.photoChallenger.tripture.domain.purchase.repository;

import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
