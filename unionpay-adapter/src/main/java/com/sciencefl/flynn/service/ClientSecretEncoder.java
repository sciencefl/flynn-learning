package com.sciencefl.flynn.service;

import com.sciencefl.flynn.common.ResultCode;
import com.sciencefl.flynn.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class ClientSecretEncoder {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public String encodeSecret(String clientSecret) {
        try {
            byte[] salt = generateSalt();
            byte[] hash = generateHash(clientSecret.toCharArray(), salt);
            return Base64.getEncoder().encodeToString(salt) + "$" +
                    Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR, "密码加密失败");
        }
    }

    public boolean matches(String rawSecret, String encodedSecret) {
        try {
            String[] parts = encodedSecret.split("\\$");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);
            byte[] testHash = generateHash(rawSecret.toCharArray(), salt);
            return Arrays.equals(hash, testHash);
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private byte[] generateHash(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        return factory.generateSecret(spec).getEncoded();
    }
}
