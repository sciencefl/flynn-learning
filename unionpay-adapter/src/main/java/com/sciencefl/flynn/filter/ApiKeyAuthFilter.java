package com.sciencefl.flynn.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    @Value("${app.api-key-header}")
    private String apiKeyHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 跳过token生成端点
        if (request.getServletPath().equals("/api/v1/ssc/auth/token")) {
            filterChain.doFilter(request, response);
            return;
        }

//        String apiKey = request.getHeader(apiKeyHeader);
//        if (apiKey != null) {
//            if (validateApiKey(apiKey)) {
//                UsernamePasswordAuthenticationToken auth =
//                        new UsernamePasswordAuthenticationToken(apiKey, null, List.of());
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }

        filterChain.doFilter(request, response);
    }

    private boolean validateApiKey(String apiKey) {
        // 实现实际的API Key校验逻辑（如查数据库）
        return true;
    }
}
