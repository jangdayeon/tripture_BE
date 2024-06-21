package com.photoChallenger.tripture.domain.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileID;
    private Integer profileQ;
    private String profileA;
    private String profileNickname;
    private Integer sex;
    @Temporal(TemporalType.DATE)
    private Date profileBirth;
    private String profileImgUrl;
    private String profileImgName;

    private Profile(Integer profileQ, String profileA, String profileNickname, Integer sex, Date profileBirth, String profileImgUrl, String profileImgName) {
        this.profileQ = profileQ;
        this.profileA = profileA;
        this.profileNickname = profileNickname;
        this.sex = sex;
        this.profileBirth = profileBirth;
        this.profileImgUrl = profileImgUrl;
        this.profileImgName = profileImgName;
    }

}
