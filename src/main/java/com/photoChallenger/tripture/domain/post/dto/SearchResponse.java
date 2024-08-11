package com.photoChallenger.tripture.domain.post.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    Long postId;
    String postImgName;
}
