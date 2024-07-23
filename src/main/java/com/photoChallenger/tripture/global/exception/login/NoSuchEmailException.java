package com.photoChallenger.tripture.global.exception.login;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchEmailException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 이메일입니다.";

    public NoSuchEmailException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
