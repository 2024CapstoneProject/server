package com.example.kioskhelper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptionService {
    @Value("${secret.key}")
    private String SECRET_KEY_STRING;// 32 bytes for AES-256
    private SecretKey secretKey;
    private Cipher cipher;

    @PostConstruct
    public void init() throws Exception {
        secretKey = new SecretKeySpec(SECRET_KEY_STRING.getBytes(), "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    public String encrypt(String strToEncrypt) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.toString());
        }
    }

    public String decrypt(String strToDecrypt) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.toString());
        }
    }
}