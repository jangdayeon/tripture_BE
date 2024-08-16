package com.photoChallenger.tripture.global.exception.redis;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class EmailAuthValidTimeOverException extends TriptureException {
    private static final String MESSAGE = "이메일 인증 유효 시간이 지났습니다.";
    public EmailAuthValidTimeOverException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
