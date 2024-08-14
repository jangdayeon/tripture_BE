package com.photoChallenger.tripture.domain.item.dto;

import com.photoChallenger.tripture.domain.item.dto.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchListResponse {
    int totalPages;
    List<SearchResponse> searchResponseList;
}
