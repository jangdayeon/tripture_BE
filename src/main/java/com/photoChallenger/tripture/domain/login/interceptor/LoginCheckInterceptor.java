package com.photoChallenger.tripture.domain.login.interceptor;

import com.photoChallenger.tripture.domain.login.dto.LoginIdResponse;
import com.photoChallenger.tripture.domain.login.entity.SessionConst;
import com.photoChallenger.tripture.domain.login.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final LoginService loginService;

    public static final String SESSION_COOKIE_NAME = "mySessionId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        Cookie cookie = WebUtils.getCookie(request, SESSION_COOKIE_NAME);
        if(cookie != null) {
            LoginIdResponse userInfo = loginService.checkLoginId(cookie.getValue());
            if(userInfo != null) {
                session.setAttribute(SessionConst.LOGIN_MEMBER, userInfo);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"로그인이 만료된 사용자입니다.\"}");
            response.getWriter().flush();
            return false;
        }

        return true;
    }
}
