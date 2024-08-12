package com.photoChallenger.tripture.global.elasticSearch.itemSearch;

import com.photoChallenger.tripture.global.elasticSearch.challengeSearch.ChallengeDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemSearchRepository extends ElasticsearchRepository<ItemDocument, Long> {
    @Query("{\"match\": {\"itemName\": {\"query\": \"?0\"}}}")
    List<ItemDocument> findAllByItemName(String itemName);
}
