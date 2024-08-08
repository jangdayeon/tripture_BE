package com.photoChallenger.tripture.global.exception.post;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchPostException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 포토챌린지입니다.";

    public NoSuchPostException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
