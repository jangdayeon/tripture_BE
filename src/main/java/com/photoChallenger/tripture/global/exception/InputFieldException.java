package com.photoChallenger.tripture.global.exception;

import org.springframework.http.HttpStatus;

public class InputFieldException extends TriptureException{
    protected static final String EMAIL = "email";
    protected static final String NICKNAME = "nickname";
    protected static final String PASSWORD = "password";
    protected static final String AUTH_NUMBER = "auth_number";
    protected static final String POINT = "point";
    protected static final String STOCK = "stock";

    private final String field;

    public InputFieldException(final String message, final HttpStatus status, final String field) {
        super(message, status);
        this.field = field;
    }
}
