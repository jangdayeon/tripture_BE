package com.photoChallenger.tripture.global.exception.item;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class OutOfStockException extends InputFieldException {
    private static final String MESSAGE = "재고가 소진된 상품입니다.";
    public OutOfStockException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, STOCK);
    }
}
