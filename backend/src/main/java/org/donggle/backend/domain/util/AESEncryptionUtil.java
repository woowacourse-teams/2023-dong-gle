package org.donggle.backend.domain.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESEncryptionUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";

    private final SecretKeySpec secretKey;
    private final IvParameterSpec iv;

    public AESEncryptionUtil(@Value("${encrypt_secret_key}") final String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        this.iv = getIVSecureRandom(ALGORITHM);
    }

    public String encrypt(final String data) {
        if (data == null) {
            return null;
        }
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(final String encryptedData) {
        if (encryptedData == null) {
            return null;
        }
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static IvParameterSpec getIVSecureRandom(final String algorithm) {
        final SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
            final byte[] iv = new byte[Cipher.getInstance(algorithm).getBlockSize()];
            random.nextBytes(iv);
            return new IvParameterSpec(iv);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
