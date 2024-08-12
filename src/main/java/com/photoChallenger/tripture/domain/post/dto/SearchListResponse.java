package com.photoChallenger.tripture.domain.post.dto;

import com.photoChallenger.tripture.domain.comment.dto.FindAllComment;
import com.photoChallenger.tripture.domain.comment.dto.FindComment;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.global.elasticSearch.challengeSearch.ChallengeDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchListResponse {
    List<SearchResponse> searchResponseList;
}
