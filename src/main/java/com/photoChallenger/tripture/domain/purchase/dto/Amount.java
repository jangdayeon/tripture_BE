package com.photoChallenger.tripture.domain.purchase.dto;

import lombok.Data;

@Data
public class Amount {
    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
    private int green_deposit;
}
