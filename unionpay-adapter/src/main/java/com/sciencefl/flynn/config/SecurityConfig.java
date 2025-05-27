package com.sciencefl.flynn.config;


import com.sciencefl.flynn.filter.ApiKeyAuthFilter;
import com.sciencefl.flynn.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 当前 SecurityConfig 配置下，Filter 执行顺序和逻辑如下：
 *
 *
 * ApiKeyAuthFilter
 *
 *
 * 所有请求首先进入 ApiKeyAuthFilter。
 * 如果请求路径是 /api/v1/ssc/auth/token，直接放行，不做任何处理。
 * 其他路径会尝试从请求头读取 API Key（X-API-KEY），如果存在则设置认证信息（但实际校验逻辑未实现，始终返回 true）。
 * JwtAuthFilter
 *
 *
 * 除 /api/v1/ssc/auth/token 外，所有请求在 ApiKeyAuthFilter 之后进入 JwtAuthFilter。
 * 尝试从 Authorization 头读取 Bearer Token，校验 JWT 合法性，合法则设置认证信息。
 * Spring Security 授权
 *
 *
 * /api/v1/ssc/auth/token 路径被 permitAll() 放行，不需要认证。
 * 其他路径要求已认证（即 SecurityContextHolder 中有认证信息），否则进入异常处理。
 * 总结：
 *
 *
 * /api/v1/ssc/auth/token 只走 ApiKeyAuthFilter，不校验 JWT。
 * 其他接口先走 ApiKeyAuthFilter（可选 API Key），再走 JwtAuthFilter（必须 JWT），最终由 Spring Security 判断是否已认证。
 * 认证失败时由自定义的异常处理器处理。
 * 执行顺序： UsernamePasswordAuthenticationFilter 之前
 * → ApiKeyAuthFilter
 * → JwtAuthFilter
 * → 业务处理
 *
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    @Autowired
    private  ApiKeyAuthFilter apiKeyAuthFilter;

    /**
     * 最终的执行顺序： JwtAuthFilter → apiKeyAuthFilter → UsernamePasswordAuthenticationFilter
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO 1. 密钥相关的加密，2. 用户信息应从后端存储中那，而不是硬编码在代码中，3. 防重放相关的加入
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/ssc/auth/token").permitAll()
                        // 所有匹配这个路径的请求都不需要认证，但需要经过下面的过滤器
                        .anyRequest().authenticated()
                )
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), ApiKeyAuthFilter.class)
                .exceptionHandling(ex -> ex
                       //  .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                         .accessDeniedHandler(new JwtAccessDeniedHandler())
                );
        return http.build();
    }
}
