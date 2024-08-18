package com.photoChallenger.tripture.global.exception.purchase;

import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class KakaoPayFailException extends TriptureException{
    private static final String MESSAGE = "사용자가 결제 실패했습니다.";
    public KakaoPayFailException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}

