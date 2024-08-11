package com.photoChallenger.tripture.global.exception.comment;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchCommentException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public NoSuchCommentException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
