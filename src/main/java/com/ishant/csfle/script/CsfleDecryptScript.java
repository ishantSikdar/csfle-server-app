package com.ishant.csfle.script;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CsfleDecryptScript {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // GCM recommended IV length is 12 bytes
    private static final int GCM_TAG_LENGTH = 16 * 8; // 16 bytes authentication tag length (128 bits)
    private static final byte[] FIXED_IV = new byte[GCM_IV_LENGTH]; // Fixed IV for deterministic encryption

    public static void main(String[] args) throws Exception {
        String keyString = "lZgM4Br7BcsMhw+2biqwqNvDVezgxRKvHtGr2c6TqDA=";
        SecretKey secretKey = stringToSecretKey(keyString);
        String ciphertext = "vxtU7HK5fgnEC0R+llIC2ohfjtVe";
        String plaintext = decrypt(ciphertext, secretKey, FIXED_IV);
        System.out.println(plaintext);
    }

    // Generate a secret key for AES
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        return keyGen.generateKey();
    }

    // Encrypt the plaintext
    public static String encrypt(String plaintext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the ciphertext
    public static String decrypt(String ciphertext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, "UTF-8");
    }


    public static String secretKeyToString(SecretKey secretKey) {
        byte[] keyBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static SecretKey stringToSecretKey(String secretKeyString) {
        byte[] storedKeyBytes = Base64.getDecoder().decode(secretKeyString);
        return new SecretKeySpec(storedKeyBytes, 0, storedKeyBytes.length, "AES");
    }
}
