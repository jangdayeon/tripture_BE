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

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long profileId;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long postId;

    @Column(columnDefinition = "longtext")
    private String reportContent;

    public Report(Long profileId, Long postId, String reportContent) {
        this.profileId = profileId;
        this.postId = postId;
        this.reportContent = reportContent;
    }

    @Builder
    public static Report create(Long profileId, Long postId, String reportContent) {
        return new Report(profileId, postId, reportContent);
    }
}
