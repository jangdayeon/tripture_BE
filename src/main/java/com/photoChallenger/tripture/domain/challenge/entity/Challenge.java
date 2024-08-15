package com.photoChallenger.tripture.domain.challenge.entity;

import com.photoChallenger.tripture.domain.post.entity.Post;
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
    @Column(columnDefinition = "varchar(10)")
    private ChallengeRegion challengeRegion;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private ChallengeType challengeType;

    @Column(nullable = false)
    private LocalDate challengeDate;

    @Column(nullable = false)
    private Integer challengePoint;

    @Column(nullable = false)
    private String contentId;

    @Column(nullable = false, columnDefinition = "decimal")
    private Float challengeLatitude;

    @Column(nullable = false, columnDefinition = "decimal")
    private Float challengeLongitude;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post = new ArrayList<>();

    private Challenge(Long challengeId, String challengeImgName, String challengeName, String challengeContent, ChallengeRegion challengeRegion, ChallengeType challengeType, LocalDate challengeDate, Integer challengePoint, String contentId, Float challengeLatitude, Float challengeLongitude) {
        this.challengeId = challengeId;
        this.challengeImgName = challengeImgName;
        this.challengeName = challengeName;
        this.challengeContent = challengeContent;
        this.challengeRegion = challengeRegion;
        this.challengeType = challengeType;
        this.challengeDate = challengeDate;
        this.challengePoint = challengePoint;
        this.contentId = contentId;
        this.challengeLatitude = challengeLatitude;
        this.challengeLongitude = challengeLongitude;
    }

    @Builder
    public static Challenge create(Long challengeId, String challengeImgName, String challengeName, String challengeContent, ChallengeRegion challengeRegion, ChallengeType challengeType, LocalDate challengeDate, Integer challengePoint, String contentId, Float challengeLatitude, Float challengeLongitude){
        Challenge challenge = new Challenge(challengeId,challengeImgName,challengeName,challengeContent,challengeRegion,challengeType,challengeDate,challengePoint,contentId,challengeLatitude,challengeLongitude);
        return challenge;
    }
}
