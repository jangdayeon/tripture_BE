package com.photoChallenger.tripture.domain.login.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoChallenger.tripture.domain.login.dto.LoginRequest;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static final String USER_ID = "userId";
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession(false);

        ObjectMapper objectMapper = new ObjectMapper();

        ServletInputStream inputStream = request.getInputStream();
        String data = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        LoginRequest loginRequest = objectMapper.readValue(data, LoginRequest.class);

        if(session != null
                && session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            log.info("login success");
            if(loginRequest.getIsAutoLogin()) {
                Cookie cookie = new Cookie(SESSION_COOKIE_NAME, session.getId());
                cookie.setPath("/");
                cookie.setMaxAge(60*60*24*90);
                response.addCookie(cookie);
            }
        }
    }
}
