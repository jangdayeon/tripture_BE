package com.photoChallenger.tripture.domain.report.dto;

import com.photoChallenger.tripture.domain.report.entity.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    ReportType reportType;
    Long postOrCommentId;
    String reportContent;
    String reportCategory;
    boolean reportBlockChk;
}
