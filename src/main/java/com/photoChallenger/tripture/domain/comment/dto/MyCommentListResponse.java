package com.photoChallenger.tripture.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyCommentListResponse {
    int totalPages;
    List<MyCommentResponse> myCommentResponses;
}
