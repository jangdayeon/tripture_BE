package com.photoChallenger.tripture.global.exception.purchase;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class AlreadyUsedItemException extends TriptureException {
    private static final String MESSAGE = "이미 사용한 아이템입니다.";
    public AlreadyUsedItemException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
