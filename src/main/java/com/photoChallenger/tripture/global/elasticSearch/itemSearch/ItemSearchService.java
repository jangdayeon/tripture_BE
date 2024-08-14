package com.photoChallenger.tripture.global.elasticSearch.itemSearch;

import com.photoChallenger.tripture.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ItemSearchService {
    private final ItemSearchRepository itemSearchRepository;

    public ItemDocument createItem(Item item) {
        return itemSearchRepository.save(ItemDocument.from(item));
    }

    public List<ItemDocument> getItemByItemName(String itemName) {
        return itemSearchRepository.findAllByItemName(itemName);
    }
}
