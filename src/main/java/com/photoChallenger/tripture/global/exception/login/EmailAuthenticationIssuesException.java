package com.photoChallenger.tripture.global.exception.login;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class EmailAuthenticationIssuesException extends InputFieldException {
    private static final String MESSAGE = "인증번호를 다시 확인해 주세요.";
    public EmailAuthenticationIssuesException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, AUTH_NUMBER);
    }
}
