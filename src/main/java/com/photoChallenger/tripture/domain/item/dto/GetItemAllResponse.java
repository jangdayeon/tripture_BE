package com.photoChallenger.tripture.domain.item.dto;

import com.photoChallenger.tripture.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetItemAllResponse {
    int totalPages;
    List<GetItemResponse> itemList;

    public static GetItemAllResponse of(int totalPages, List<Item> itemAllList) {
        List<GetItemResponse> items = itemAllList.stream()
                .map(GetItemResponse::from).toList();
        return new GetItemAllResponse(totalPages, items);
    }
}
