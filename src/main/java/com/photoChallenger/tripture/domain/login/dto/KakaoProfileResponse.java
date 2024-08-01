package com.photoChallenger.tripture.domain.login.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public class KakaoProfileResponse {
    private String email;

    public KakaoProfileResponse(String jsonResponseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponseBody);

        this.email = jsonNode.get("kakao_account").get("email").asText();
    }
}
