package com.photoChallenger.tripture.domain.postCnt.entity;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class PostCnt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private long postCntId;

    private Integer inc;
    private Integer seo;
    private Integer jeon;
    private Integer gang;
    private Integer chung;
    private Integer gyeong;
    private Integer je;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    protected PostCnt(){
        this.inc = 0;
        this.seo = 0;
        this.jeon = 0;
        this.gang = 0;
        this.chung = 0;
        this.gyeong = 0;
        this.je = 0;
    }

    public static PostCnt create() {
        PostCnt postCnt = new PostCnt();
        return postCnt;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public PostCnt update(String where, Integer cnt){
        switch (where){
            case "inc" :
                this.inc += cnt;
                break;
            case "seo" :
                this.seo += cnt;
                break;
            case "jeon" :
                this.jeon += cnt;
                break;
            case "gang" :
                this.gang += cnt;
                break;
            case "chung" :
                this.chung += cnt;
                break;
            case "gyeong" :
                this.gyeong += cnt;
                break;
            case "je" :
                this.je += cnt;
                break;
        }
        return this;
    }
}
