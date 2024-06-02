package com.ishant.csfle.service.impl;

import com.ishant.csfle.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Value("${encryption.master-key}")
    private String MASTER_KEY;

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // GCM recommended IV length is 12 bytes
    private static final int GCM_TAG_LENGTH = 16 * 8; // 16 bytes authentication tag length (128 bits)
    private static final byte[] FIXED_IV = new byte[GCM_IV_LENGTH]; // Fixed IV for deterministic encryption


    @Override
    public SecretKey getMasterKey() {
        log.debug("Master Key: {}", MASTER_KEY);
        return stringToSecretKey(MASTER_KEY);
    }

    @Override
    public SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        return keyGen.generateKey();
    }

    @Override
    public String encrypt(String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, FIXED_IV);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String ciphertext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, FIXED_IV);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    @Override
    public String secretKeyToString(SecretKey secretKey) {
        byte[] keyBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    @Override
    public SecretKey stringToSecretKey(String secretKeyString) {
        byte[] storedKeyBytes = Base64.getDecoder().decode(secretKeyString);
        return new SecretKeySpec(storedKeyBytes, 0, storedKeyBytes.length, "AES");
    }
}
