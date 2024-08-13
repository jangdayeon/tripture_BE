package com.photoChallenger.tripture.domain.item.repository;

import com.photoChallenger.tripture.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.ContentHandler;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.itemId IN (:itemIds)")
    Page<Item> findAllByItem_ItemId(List<Long> itemIds, Pageable pageable);
}
