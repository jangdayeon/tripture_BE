package com.photoChallenger.tripture.global.elasticSearch.challengeSearch;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeSearchService {
    private final ChallengeSearchRepository challengeSearchRepository;

    public ChallengeDocument createItem(Challenge challenge) {
        return challengeSearchRepository.save(ChallengeDocument.from(challenge));
    }

    public List<ChallengeDocument> getChallengeByChallengeName(String challengeName) {
        return challengeSearchRepository.findAllByChallengeName(challengeName);
    }
}
