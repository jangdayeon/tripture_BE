package com.photoChallenger.tripture.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TriptureException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "일시적인 오류가 발생했습니다.";

    private final HttpStatus status;

    public TriptureException() {
        super(DEFAULT_MESSAGE);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public TriptureException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public TriptureException(final String message, final Throwable cause, final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
