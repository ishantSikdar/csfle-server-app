package com.ishant.csfle.service;

import javax.crypto.SecretKey;

public interface EncryptionService {
    SecretKey getMasterKey();

    SecretKey generateKey() throws Exception;

    String encrypt(String plaintext, SecretKey key) throws Exception;

    String decrypt(String ciphertext, SecretKey key) throws Exception;

    String secretKeyToString(SecretKey secretKey);

    SecretKey stringToSecretKey(String secretKeyString);
}
