package org.donggle.backend.domain.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AESEncryptionUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";

    private final SecretKeySpec secretKey;
    private final IvParameterSpec iv;

    public AESEncryptionUtil(@Value("${encrypt_secret_key}") final String secretKey, @Value("${encrypt_iv}") final String iv) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        this.iv = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
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
}
