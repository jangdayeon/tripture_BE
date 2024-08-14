package com.photoChallenger.tripture.global.elasticSearch.itemSearch;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.global.elasticSearch.challengeSearch.ChallengeDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elastic/elastic-setting.json")
@Mapping(mappingPath = "elastic/elastic-mapping.json")
@Document(indexName = "items")
public class ItemDocument {
    @Id
    private Long itemId;
    private String itemName;

    public static ItemDocument from(Item item){
        return ItemDocument.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .build();
    }
}
