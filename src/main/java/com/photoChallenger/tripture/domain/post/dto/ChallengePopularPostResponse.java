package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

import static com.photoChallenger.tripture.global.S3.S3Url.S3_URL;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengePopularPostResponse {
    private Long profileId;
    private Long postId;
    private String postImgName;
    private String contentId;
    private Boolean blockChk;

    public static ChallengePopularPostResponse of(Post post, Boolean isBlocked){
        return new ChallengePopularPostResponse(
                post.getProfile().getProfileId(),
                post.getPostId(),
                S3_URL+post.getPostImgName(),
                post.getContentId(),
                isBlocked);
    }
}
