package com.photoChallenger.tripture.domain.bookmark.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPhotoChallengeResponse {
    Long postId;
    String postImgName;

    public static MyPhotoChallengeResponse from(Post post){
        return new MyPhotoChallengeResponse(post.getPostId(), "${cloud.aws.url}"+post.getPostImgName());
    }
}
