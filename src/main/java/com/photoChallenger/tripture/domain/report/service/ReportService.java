package com.photoChallenger.tripture.domain.report.service;

import com.photoChallenger.tripture.domain.report.dto.ReportRequest;

public interface ReportService {

    // 신고 등록
    String registerUserReporting(ReportRequest requestRequest, Long loginId);
}
