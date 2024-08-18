package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PopularPostResponse {
    Long profileId;
    Long postId;
    String postImgName;
    Boolean blockChk;

    static PopularPostResponse of(Post post, Boolean isBlocked){
        return new PopularPostResponse(
                post.getProfile().getProfileId(),
                post.getPostId(),
                S3Url.S3_URL+post.getPostImgName(),
                isBlocked);
    }
}
