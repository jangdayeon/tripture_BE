package com.photoChallenger.tripture.domain.challenge.entity;

import com.photoChallenger.tripture.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private String challengeImgUrl;

    private String challengeImgName;

    @Column(nullable = false)
    private String challengeName;

    private String challengeContent;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private ChallengeRegion challengeRegion;

    @Column(nullable = false)
    private LocalDate challengeDate;

    @Column(nullable = false)
    private Integer challengePoint;

    @Column(nullable = false)
    private String contentId;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post = new ArrayList<>();
}
