package com.photoChallenger.tripture.global.exception;

import org.springframework.http.HttpStatus;

public class InputFieldException extends TriptureException{
    protected static final String EMAIL = "email";
    protected static final String POINT = "point";
    protected static final String STOCK = "stock";

    private final String field;

    public InputFieldException(final String message, final HttpStatus status, final String field) {
        super(message, status);
        this.field = field;
    }
}
