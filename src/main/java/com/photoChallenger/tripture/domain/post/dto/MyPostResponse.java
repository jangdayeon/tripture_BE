package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPostResponse {
    Long postId;
    String postImgName;

    @Value("${cloud.aws.url}")
    static String domain;
    public static MyPostResponse from(Post post){
        return new MyPostResponse(post.getPostId(), domain+post.getPostImgName());
    }
}
