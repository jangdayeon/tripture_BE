package com.photoChallenger.tripture.global.config;

import com.photoChallenger.tripture.domain.login.interceptor.LoginCheckInterceptor;
import com.photoChallenger.tripture.domain.login.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .order(1)
                .addPathPatterns("/login");

        registry.addInterceptor(loginCheckInterceptor)
                .order(2)
                .addPathPatterns("/**");
    }
}
