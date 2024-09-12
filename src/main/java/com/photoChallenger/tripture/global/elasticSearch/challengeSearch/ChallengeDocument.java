package com.photoChallenger.tripture.global.elasticSearch.challengeSearch;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elastic/elastic-setting.json")
@Mapping(mappingPath = "elastic/elastic-mapping.json")
@Document(indexName = "posts")
public class ChallengeDocument {
    @Id
    private Long postId;
    private String postChallengeName;

    public static ChallengeDocument from(Post post){
        return ChallengeDocument.builder()
                .postId(post.getPostId())
                .postChallengeName(post.getPostChallengeName())
                .build();
    }
}
