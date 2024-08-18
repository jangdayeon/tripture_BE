package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopularPostListResponse {
    int totalPages;
    List<PopularPostResponse> popularCalculateDtoList;

    public static PopularPostListResponse of(int totalPages, List<Post> postList, Set<Long> blockList) {
        List<PopularPostResponse> popularPostResponses = postList.stream()
                .map(popularPost -> {
                    boolean isBlocked = blockList.contains(popularPost.getProfile().getProfileId());
                    return PopularPostResponse.of(popularPost, isBlocked);
                }).toList();
        return new PopularPostListResponse(totalPages,popularPostResponses);
    }
}
