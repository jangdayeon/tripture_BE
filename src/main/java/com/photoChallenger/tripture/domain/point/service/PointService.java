package com.photoChallenger.tripture.domain.point.service;

import com.photoChallenger.tripture.domain.point.dto.PointDto;
import com.photoChallenger.tripture.domain.point.dto.PointListResponse;

import java.time.LocalDate;
import java.util.List;

public interface PointService {
    PointListResponse getPointList(int pageNo, LocalDate startDate, LocalDate endDate, Long loginId);
}
