package com.photoChallenger.tripture.domain.report.repository;

import com.photoChallenger.tripture.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository  extends JpaRepository<Report, Long> {
}
