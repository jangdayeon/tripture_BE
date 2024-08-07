package com.photoChallenger.tripture.global.exception.point;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class LackPointException extends InputFieldException {
    private static final String MESSAGE = "포인트가 부족합니다.";
    public LackPointException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, POINT);
    }
}
