package com.photoChallenger.tripture.global.exception.item;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchItemException extends TriptureException {
    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public NoSuchItemException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
