package com.photoChallenger.tripture.global.exception.redis;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class AlreadyCheckUserException extends TriptureException {
    private static final String MESSAGE = "이미 조회된 사용자입니다.";
    public AlreadyCheckUserException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
