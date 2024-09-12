package com.photoChallenger.tripture.domain.challenge.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Challenge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long challengeId;

    private String challengeImgName;

    @Column(nullable = false)
    private String challengeName;

    private String challengeContent;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private ChallengeType challengeType;

    @Column(nullable = false)
    private LocalDate challengeDate;

    @Column(nullable = false)
    private Integer challengePoint;

    @Column(nullable = false)
    private String contentId;

    private Challenge(Long challengeId, String challengeImgName, String challengeName, String challengeContent, ChallengeType challengeType, LocalDate challengeDate, Integer challengePoint, String contentId) {
        this.challengeId = challengeId;
        this.challengeImgName = challengeImgName;
        this.challengeName = challengeName;
        this.challengeContent = challengeContent;
        this.challengeType = challengeType;
        this.challengeDate = challengeDate;
        this.challengePoint = challengePoint;
        this.contentId = contentId;
    }

    @Builder
    public static Challenge create(Long challengeId, String challengeImgName, String challengeName, String challengeContent, ChallengeType challengeType, LocalDate challengeDate, Integer challengePoint, String contentId){
        Challenge challenge = new Challenge(challengeId,challengeImgName,challengeName,challengeContent,challengeType,challengeDate,challengePoint,contentId);
        return challenge;
    }
}
