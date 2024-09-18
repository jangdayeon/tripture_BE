package com.photoChallenger.tripture.domain.bookmark.dto;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.bookmark.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetContentListResponse {
    List<MyContentResponse> contentBookmarkList;

    public static GetContentListResponse of(List<Content> bookmarkList) {
        return new GetContentListResponse(bookmarkList.stream()
                .map(content -> new MyContentResponse(content.getContentId())).toList());
    }
}
