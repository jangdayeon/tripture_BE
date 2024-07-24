package com.photoChallenger.tripture.global.exception.global;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import com.photoChallenger.tripture.global.exception.TriptureException;
import org.springframework.http.HttpStatus;

public class S3IOException extends TriptureException {
    private static final String MESSAGE = "S3를 이용할 수 없습니다.";

    public S3IOException(){
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
