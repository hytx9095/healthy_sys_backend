package com.naiyin.healthy.config;

import com.naiyin.healthy.interceptor.AuthInterceptorHandler;
import com.naiyin.healthy.interceptor.TestInterceptorHandler;
import com.naiyin.healthy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptorHandler())
//                .addPathPatterns("/**");
        registry.addInterceptor(authInterceptorHandler())
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/findPassword")
                .excludePathPatterns("/**/register")
                .excludePathPatterns("/swagger-ui.html", "/swagger-resources/**"
                        , "/webjars/**", "/swagger-ui/**", "/v2/api-docs/**", "/doc.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求
        registry.addMapping("/**")
                // 允许发送 Cookie
                .allowCredentials(true)
                // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Bean
    public AuthInterceptorHandler authInterceptorHandler() {
        return new AuthInterceptorHandler(jwtProperties, userService);
    }

    @Bean
    public TestInterceptorHandler testInterceptorHandler() {
        return new TestInterceptorHandler();
    }
}
