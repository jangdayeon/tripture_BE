package com.photoChallenger.tripture.global.elasticSearch.challengeSearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeSearchRepository extends ElasticsearchRepository<ChallengeDocument, Long> {

    @Query("{\"match\": {\"challengeName\": {\"query\": \"?0\"}}}")
    List<ChallengeDocument> findAllByChallengeName(String challengeName);
}
