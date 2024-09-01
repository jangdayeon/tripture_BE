package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.profile.entity.ProfileLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.photoChallenger.tripture.global.S3.S3Url.S3_URL;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    Long profileId;
    String nickname;
    ProfileLevel level;
    String profileImgUrl;
    String imgUrl;
    String postContent;
    Integer postLikeCount;
    Integer postCommentCount;
    String contentId;
    String isMyPost;
    String isSaveBookmark;
    String isLike;

    @Builder
    public static GetPostResponse of(Long profileId, String nickname, ProfileLevel level, String profileImgUrl, String imgName,
                                     String postContent, Integer postLikeCount, Integer postCommentCount, String contentId, String isMyPost, String isSaveBookmark, String isLike) {
        return new GetPostResponse(profileId, nickname, level, S3_URL + profileImgUrl, S3_URL + imgName, postContent, postLikeCount, postCommentCount, contentId, isMyPost, isSaveBookmark, isLike);
    }
}
