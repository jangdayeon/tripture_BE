package com.photoChallenger.tripture.domain.profile.entity;

public enum ProfileLevel {
    LEVEL1("레벨1 찰칵 루키"),
    LEVEL2("레벨2 챌린지 스타"),
    LEVEL3("레벨3 스냅 마스터");

    private final String description;

    ProfileLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
