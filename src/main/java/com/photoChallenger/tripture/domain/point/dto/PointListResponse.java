package com.photoChallenger.tripture.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PointListResponse {
    private int totalPages;
    private List<PointDto> pointDtos;
}
