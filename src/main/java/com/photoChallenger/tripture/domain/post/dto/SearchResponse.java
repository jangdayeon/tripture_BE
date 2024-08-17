package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SearchResponse {
    Long postId;
    String postImgName;

    public SearchResponse(Long postId, String postImgName) {
        this.postId = postId;
        this.postImgName = S3Url.S3_URL + postImgName;
    }
}
