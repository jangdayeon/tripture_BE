package com.photoChallenger.tripture.global.elasticSearch.challengeSearch;

import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elastic/elastic-setting.json")
@Mapping(mappingPath = "elastic/elastic-mapping.json")
@Document(indexName = "challenges")
public class ChallengeDocument {
    @Id
    private Long challengeId;
    private String challengeName;

    public static ChallengeDocument from(Challenge challenge){
        return ChallengeDocument.builder()
                .challengeId(challenge.getChallengeId())
                .challengeName(challenge.getChallengeName())
                .build();
    }
}
