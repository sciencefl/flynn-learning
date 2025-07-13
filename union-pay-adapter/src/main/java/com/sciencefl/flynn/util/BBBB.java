package com.sciencefl.flynn.util;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;

public class BBBB {
    public static void main(String[] args) {
        String data ="{\"babyInfo\":{\"certifTp\":\"01\",\"certifId\":\"123222xxx\",\"customerNm\":\"刘备\"},\"parentInfo\":{\"certifTp\":\"01\",\"certifId\":\"123222xxx\",\"customerNm\":\"刘备\",\"userId\":\"22121233\",\"phoneNo\":\"123939383838\"},\"cardInfo\":{\"accNo\":\"1111232324324324323432432\"}}";
        // 创建SM2对象


        // 生成密钥对  通过对应的certId 找到
        String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEJxsDuxGIxC2agL9ocsE4pJG72BYjzld+JAveTB5PmVqSNrIaqztrnEYFtjuWsstxBWE61CCJ0/Zf7rAZA1Oksg==";
        String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgPI+e4JH5l1OuDii+t3zeWYXyVHylRvq6zxClGYQwnAWgCgYIKoEcz1UBgi2hRANCAAQnGwO7EYjELZqAv2hywTikkbvYFiPOV34kC95MHk+ZWpI2shqrO2ucRgW2O5ayy3EFYTrUIInT9l/usBkDU6Sy";
        SM2 sm2 = SmUtil.sm2(privateKey,publicKey);
        String sourceKey  = "1234567890123456"; // 16字节的源密钥
        SM4 sm4 = new SM4(sourceKey.getBytes());
        // 使用SM4加密
        String encryptedData = sm4.encryptBase64(data); // data
        // 使用公钥加密
        String key = sm2.encryptBase64(sourceKey, KeyType.PublicKey); // sourceKey
        System.out.println("encryptedKey Data: " + key);
        String encryptMethod = "SM2";

        // 使用私钥解密
        String decryptedData = sm2.decryptStr(key,KeyType.PrivateKey);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
