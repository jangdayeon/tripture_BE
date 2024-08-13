package com.photoChallenger.tripture.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyPhotoChallengeListResponse {
    int totalPages;
    List<MyPhotoChallengeResponse> myPhotoChallengeResponseList;
}
