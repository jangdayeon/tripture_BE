package com.photoChallenger.tripture.global.exception.login;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchLoginException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NoSuchLoginException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
