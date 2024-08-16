package com.photoChallenger.tripture.domain.purchase.entity;

import com.photoChallenger.tripture.domain.purchase.dto.KakaoPaySessionDto;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class SessionUtils {
    public static void addAttribute(String name, KakaoPaySessionDto value) {
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    public static String getStringAttributeValue(String name) {
        return String.valueOf(getAttribute(name));
    }

    public static KakaoPaySessionDto getAttribute(String name) {
        return (KakaoPaySessionDto) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
}
