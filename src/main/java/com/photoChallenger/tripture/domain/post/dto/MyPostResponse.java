package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.global.S3.S3Url;
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

    public static MyPostResponse from(Post post){
        return new MyPostResponse(post.getPostId(), S3Url.S3_URL+post.getPostImgName());
    }
}
