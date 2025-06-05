package com.sciencefl.flynn.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.dao.entity.UnionPayOauthClient;
import com.sciencefl.flynn.exception.SecurityException;
import com.sciencefl.flynn.service.OAuthClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Slf4j
@Component
public class OAuth2Interceptor implements HandlerInterceptor {
    @Autowired
    private OAuthClientService clientService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Missing token");
        }

        String token = auth.substring(7);

//        String lockKey = "oauth2:lock:" + token;
        try {
//            //使用分布式锁防止并发访问
//            boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);
//            if (!locked) {
//                throw new SecurityException(ResultCode.UNAUTHORIZED, "Token is being processed");
//            }

            // 验证token
            String clientId = (String) StpUtil.getLoginIdByToken(token);
            log.info("[OAuth] Request authorization - uri:{}, clientId:{}, method:{}", request.getRequestURI(), clientId, request.getMethod());
            if (clientId == null) {
                throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid token");
            }

            UnionPayOauthClient client = clientService.getClientById(clientId);
            if (client == null || !client.getEnabled()) {
                throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid client");
            }
            // 从session获取scope信息
            List<String> scopes = client.getScopes();

            // 验证接口所需scope
            String requiredScope = getRequiredScope(request.getRequestURI());
            if (requiredScope != null && !hasRequiredScope(scopes, requiredScope)) {
                log.info("[OAuth] Request authorization - uri:{}, clientId:{}, method:{}", request.getRequestURI(), clientId, request.getMethod());
                throw new SecurityException(ResultCode.FORBIDDEN, "Insufficient scope: " + requiredScope);
            }
            return true;
        }catch (Exception e) {
            throw e;
        }
        finally {
//            redisTemplate.delete(lockKey);
        }
    }
    private String getRequiredScope(String uri) {
        // 基于URI路径匹配所需的scope权限
        if (uri.startsWith("/api/v1/ssc/batches")) {
            return "push_batchdata";
        } else if (uri.startsWith("/api/v1/ssc/oauth2/client")) {
            return "manage_clients";
        }
        // 没有特殊scope要求的接口返回null
        return null;
    }

    private boolean hasRequiredScope(List<String> grantedScopes, String requiredScope) {
        // 检查授权列表为空的情况
        if (grantedScopes == null || grantedScopes.isEmpty()) {
            return false;
        }

        // 检查是否包含所需scope
        // 1. 检查是否有all权限
        if (grantedScopes.contains("all")) {
            return true;
        }

        // 2. 检查具体权限
        return grantedScopes.contains(requiredScope);
    }
}
