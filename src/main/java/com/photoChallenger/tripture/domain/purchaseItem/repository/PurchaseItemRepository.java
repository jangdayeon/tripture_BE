package com.photoChallenger.tripture.domain.purchaseItem.repository;

import com.photoChallenger.tripture.domain.purchaseItem.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
