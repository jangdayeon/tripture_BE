package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPostResponse {
    Long postId;
    String postImgName;

    public static MyPostResponse from(Post post){
        return new MyPostResponse(post.getPostId(), "${cloud.aws.url}"+post.getPostImgName());
    }
}
