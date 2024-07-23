package com.photoChallenger.tripture.global.exception.login;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class IdPasswordMismatchException extends InputFieldException {
    private static final String MESSAGE = "이메일 혹은 비밀번호를 다시 확인해 주세요.";
    public IdPasswordMismatchException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, PASSWORD);
    }
}
