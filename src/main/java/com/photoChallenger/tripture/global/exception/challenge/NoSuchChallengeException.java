package com.photoChallenger.tripture.global.exception.challenge;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchChallengeException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 포토챌린지입니다.";

    public NoSuchChallengeException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
