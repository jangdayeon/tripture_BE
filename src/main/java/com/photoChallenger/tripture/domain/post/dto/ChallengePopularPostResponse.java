package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.global.S3.S3Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengePopularPostResponse {
    private Long profileId;
    private Long postId;
    private String postImgName;
    private String challengePoint;
    private Boolean blockChk;

    public static ChallengePopularPostResponse of(Post post, Boolean isBlocked){
        String[] challengeName = post.getChallenge().getChallengeName().split(" ");
        String point = String.join(" ", Arrays.copyOfRange(challengeName,1,challengeName.length));
        return new ChallengePopularPostResponse(
                post.getProfile().getProfileId(),
                post.getPostId(),
                S3Url.S3_URL+post.getPostImgName(),
                point,
                isBlocked);
    }
}
