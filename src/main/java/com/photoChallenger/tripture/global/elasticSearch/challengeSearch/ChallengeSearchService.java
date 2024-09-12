package com.photoChallenger.tripture.global.elasticSearch.challengeSearch;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeSearchService {
    private final ChallengeSearchRepository challengeSearchRepository;

    public ChallengeDocument createItem(Post post) {
        return challengeSearchRepository.save(ChallengeDocument.from(post));
    }

    public List<ChallengeDocument> getPostByPostChallengeName(String challengeName) {
        return challengeSearchRepository.findAllByPostChallengeName(challengeName);
    }
}
