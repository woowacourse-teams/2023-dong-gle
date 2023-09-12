package org.donggle.backend.domain.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
class AESEncryptionUtilTest {
    @Autowired
    private AESEncryptionUtil aesEncryptionUtil;

    @Test
    @DisplayName("암호화 테스트")
    void encryptData() {
        //given
        final String data = "test";

        //when
        final String encryptedData = aesEncryptionUtil.encrypt(data);

        //then
        assertSoftly(softly -> {
            softly.assertThat(encryptedData).isNotEqualTo(data);
            softly.assertThat(encryptedData).isEqualTo(aesEncryptionUtil.encrypt(data));
        });
    }

    @Test
    @DisplayName("복호화 테스트")
    void decryptData() {
        //given
        final String data = "test";
        final String encryptedData = aesEncryptionUtil.encrypt(data);

        //when
        final String decryptedData = aesEncryptionUtil.decrypt(encryptedData);

        //then
        assertThat(decryptedData).isEqualTo(data);
    }
}