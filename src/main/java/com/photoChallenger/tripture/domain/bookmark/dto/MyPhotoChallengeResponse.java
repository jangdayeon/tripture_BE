package com.photoChallenger.tripture.domain.bookmark.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPhotoChallengeResponse {
    Long postId;
    String postImgName;

    @Value("${cloud.aws.url}")
    static String domain;

    public static MyPhotoChallengeResponse from(Post post){
        return new MyPhotoChallengeResponse(post.getPostId(), domain+post.getPostImgName());
    }
}
