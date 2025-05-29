package com.sciencefl.flynn.config;


import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.exception.SecurityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Getter
    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKeySpec secretKeySpec;

    private SecretKey getSigningKey() {
        if (secretKeySpec != null) {
            return secretKeySpec;
        }
        byte[] keyBytes = Arrays.copyOf(secretKey.getBytes(), 32);
        secretKeySpec =  new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
        return secretKeySpec;
    }
    // 生成JWT
    public String generateToken(ApiKeyModel apiKeyModel) {
        try {
            Date now = new Date();
            Date expiry = new Date(now.getTime() + expirationMs);
            return Jwts.builder()
                    .setSubject(apiKeyModel.getApiKey())
                    .setIssuedAt(now)
                    .claim("scopes", apiKeyModel.getScopes())
                    .claim("appName", apiKeyModel.getAppName())
                    .setExpiration(expiry)
                    .signWith(getSigningKey())
                    .compact();
        } catch (Exception e) {
            throw new SecurityException(ResultCode.INTERNAL_ERROR, "Token generation failed");
        }
    }

    // 验证JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Token has expired");
        } catch (JwtException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid JWT token");
        } catch (IllegalArgumentException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid token format");
        }
    }

    // 从JWT中提取Claims
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Token has expired");
        } catch (JwtException | IllegalArgumentException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED, "Invalid JWT token");
        }
    }
}
