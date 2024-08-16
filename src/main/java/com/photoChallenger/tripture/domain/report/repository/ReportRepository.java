package com.photoChallenger.tripture.domain.report.repository;

import com.photoChallenger.tripture.domain.report.entity.Report;
import com.photoChallenger.tripture.domain.report.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ReportRepository  extends JpaRepository<Report, Long> {

    @Query("SELECT r.postOrCommentId FROM Report r " +
            "WHERE r.reporterId = :reporterId AND r.reportType = :reportType AND r.reportBlockChk = :reportBlockChk")
    Set<Long> findAllByReporterIdAndReportTypeAndReportBlockChk(@Param("reporterId") Long reporterId,
                                                                @Param("reportType") ReportType reportType,
                                                                @Param("reportBlockChk") boolean reportBlockChk);
}
