package com.photoChallenger.tripture.domain.bookmark.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Data
public class MyPhotoChallengeResponse {
    Long postId;
    String postImgName;

    public static MyPhotoChallengeResponse from(Post post){
        return new MyPhotoChallengeResponse(post.getPostId(), S3Url.S3_URL +post.getPostImgName());
    }
}
