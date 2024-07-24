package com.photoChallenger.tripture.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyContentResponse {
    String contentId;
    public static MyContentResponse of(String contentId){
        return new MyContentResponse(contentId);
    }
}
