package com.photoChallenger.tripture.global.exception.purchase;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class NoSuchKakaoPayResponseException extends TriptureException {
    private static final String MESSAGE = "카카오페이 결제 오류";
    public NoSuchKakaoPayResponseException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
