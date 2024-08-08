package com.photoChallenger.tripture.domain.postCnt.dto;

import com.photoChallenger.tripture.domain.postCnt.entity.ChallengeCnt;
import com.photoChallenger.tripture.domain.postCnt.entity.PostCnt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostChallengeCntResponse {
    private Integer[] inc;
    private Integer[] seo;
    private Integer[] jeon;
    private Integer[] gang;
    private Integer[] chung;
    private Integer[] gyeong;
    private Integer[] je;

    public static PostChallengeCntResponse from(PostCnt postCnt){
        return PostChallengeCntResponse.builder()
                .inc(new Integer[]{postCnt.getInc(),ChallengeCnt.inc})
                .seo(new Integer[]{postCnt.getSeo(),ChallengeCnt.seo})
                .jeon(new Integer[]{postCnt.getJeon(),ChallengeCnt.jeon})
                .gang(new Integer[]{postCnt.getGang(),ChallengeCnt.gang})
                .chung(new Integer[]{postCnt.getChung(),ChallengeCnt.chung})
                .gyeong(new Integer[]{postCnt.getGyeong(),ChallengeCnt.gyeong})
                .je(new Integer[]{postCnt.getJe(),ChallengeCnt.je})
                .build();
    }
}
