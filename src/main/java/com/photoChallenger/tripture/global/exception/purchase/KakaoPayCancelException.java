package com.photoChallenger.tripture.global.exception.purchase;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class KakaoPayCancelException extends TriptureException {
    private static final String MESSAGE = "사용자가 결제 취소했습니다.";
    public KakaoPayCancelException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
