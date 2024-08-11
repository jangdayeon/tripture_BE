package com.photoChallenger.tripture.domain.report.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long reportId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10)")
    private ReportType reportType;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long reportBlockId;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long reporterId;

    @Column(columnDefinition = "longtext")
    private String reportContent;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String reportCategory;

    @Column(nullable = false, updatable = false, columnDefinition = "TINYINT(1)")
    private Boolean reportBlockChk;

    public Report(Long reportId, ReportType reportType, Long reportBlockId, Long reporterId, String reportContent, String reportCategory, Boolean reportBlockChk) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportBlockId = reportBlockId;
        this.reporterId = reporterId;
        this.reportContent = reportContent;
        this.reportCategory = reportCategory;
        this.reportBlockChk = reportBlockChk;
    }

    @Builder
    public static Report create(Long reportId, ReportType reportType, Long reportBlockId, Long reporterId, String reportContent, String reportCategory, Boolean reportBlockChk) {
        return new Report(reportId, reportType, reportBlockId, reporterId, reportContent, reportCategory, reportBlockChk);
    }
}
