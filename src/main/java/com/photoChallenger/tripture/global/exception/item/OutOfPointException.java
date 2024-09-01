package com.photoChallenger.tripture.global.exception.item;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class OutOfPointException extends InputFieldException {
    private static final String MESSAGE = "포인트가 부족합니다.";
    public OutOfPointException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, STOCK);
    }
}