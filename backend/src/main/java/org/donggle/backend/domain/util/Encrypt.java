package org.donggle.backend.domain.util;

import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Encrypt implements AttributeConverter<String, String> {
    private final AESEncryptionUtil aesEncryptionUtil;

    @Override
    public String convertToDatabaseColumn(final String attribute) {
        return aesEncryptionUtil.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(final String dbData) {
        return aesEncryptionUtil.decrypt(dbData);
    }
}
