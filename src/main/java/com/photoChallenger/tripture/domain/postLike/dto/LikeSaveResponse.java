package com.photoChallenger.tripture.domain.postLike.dto;

import com.photoChallenger.tripture.domain.bookmark.dto.BookmarkSaveResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeSaveResponse {
    String checkDeleteOrSave;

    public static LikeSaveResponse of(String checkDeleteOrSave) {
        return new LikeSaveResponse(checkDeleteOrSave);
    }
}
