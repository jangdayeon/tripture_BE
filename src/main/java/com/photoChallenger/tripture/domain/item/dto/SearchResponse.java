package com.photoChallenger.tripture.domain.item.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    Long itemId;
    String itemImgName;
    String itemName;
    String itemPosition;
    Integer itemPrice;
}
