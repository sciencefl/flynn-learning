package com.sciencefl.flynn.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.exception.SecurityException;
import com.sciencefl.flynn.service.OAuthClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OAuth2Interceptor implements HandlerInterceptor {
    @Autowired
    private OAuthClientService clientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Missing token");
        }

        String token = auth.substring(7);
        String clientId = (String) StpUtil.getLoginIdByToken(token);

        UnionPayOauthClient client = clientService.getClientById(clientId);
        if (client == null || !client.getEnabled()) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid token");
        }

        return true;
    }
}
