package com.sciencefl.flynn.config;

import com.sciencefl.flynn.interceptor.LogInterceptor;
import com.sciencefl.flynn.interceptor.OAuth2Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private OAuth2Interceptor oAuth2Interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 首先添加日志拦截器，拦截所有请求
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**")  // 拦截所有路径
                .order(1);  // 设置优先级为1，最先执行

        // 2. 然后添加 OAuth2 拦截器
        registry.addInterceptor(oAuth2Interceptor)
                .addPathPatterns("/**")  // 拦截所有路径
                .excludePathPatterns(
                        "/api/v1/ssc/oauth2/client",     // 排除创建客户端接口
                        "/api/v1/ssc/oauth2/token"       // 排除获取token接口
                )
                .order(2);  // 设置优先级为2，在日志拦截器之后执行
    }
}
