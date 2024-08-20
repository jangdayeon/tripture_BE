package com.photoChallenger.tripture.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkSaveResponse {
    String checkDeleteOrSave;

    public static BookmarkSaveResponse of(String checkDeleteOrSave) {
        return new BookmarkSaveResponse(checkDeleteOrSave);
    }
}
