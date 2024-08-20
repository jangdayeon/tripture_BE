package com.photoChallenger.tripture.global.exception.login;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoAuthenticateEmail extends TriptureException {
    private static final String MESSAGE = "이메일 인증이 되지 않은 이메일입니다.";

    public NoAuthenticateEmail() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
