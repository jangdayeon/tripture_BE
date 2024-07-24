package com.photoChallenger.tripture.global.exception.global;

import com.photoChallenger.tripture.global.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class S3IOException extends InputFieldException {
    private static final String MESSAGE = "S3를 이용할 수 없습니다.";

    public S3IOException(){
        super(MESSAGE, HttpStatus.BAD_REQUEST, PASSWORD);
    }
}
