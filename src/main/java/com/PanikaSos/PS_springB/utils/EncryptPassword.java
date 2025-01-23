package com.PanikaSos.PS_springB.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class EncryptPassword implements PasswordEncoder {

    private static final String key = "08wR?!5!S6_WO&-v$f#0RUdrEfRoc1Th";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String salt = "huw1zOQ@a*&t8tr83e$16hiy#k+v1!0cr";
    private static SecretKey secretKeyTemp;

    public EncryptPassword() {
        SecretKeyFactory secretKeyFactory;
        KeySpec keySpec;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            keySpec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing secret key", e);
        }
    }

    public String encrypt(String input) {
        if (secretKeyTemp == null) {
            throw new IllegalStateException("Secret key is not initialized.");
        }
        byte[] iv = new byte[16]; // (rellenado con ceros)
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar", e);
        }
    }

    public String decrypt(String encryptedInput) {
        if (secretKeyTemp == null) {
            throw new IllegalStateException("Secret key is not initialized.");
        }
        byte[] iv = new byte[16];
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeyTemp, ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar", e);
        }
    }


    @Override
    public String encode(CharSequence rawPassword) {
        return encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encryptedRawPassword = encrypt(rawPassword.toString());
        return encryptedRawPassword.equals(encodedPassword);
    }
}
