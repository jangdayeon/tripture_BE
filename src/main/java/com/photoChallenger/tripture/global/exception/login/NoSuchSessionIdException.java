package com.photoChallenger.tripture.global.exception.login;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchSessionIdException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 세션Id 입니다.";

    public NoSuchSessionIdException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
