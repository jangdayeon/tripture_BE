package com.photoChallenger.tripture.domain.item.repository;

import com.photoChallenger.tripture.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
