package com.photoChallenger.tripture.domain.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailCheckDto {
    private String email;
    private String authNum;
}
