package org.donggle.backend.auth.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.donggle.backend.auth.exception.EmptyAuthorizationHeaderException;
import org.donggle.backend.auth.exception.NoSuchTokenException;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public static String extract(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader)) {
            throw new EmptyAuthorizationHeaderException();
        }

        validateAuthorizationFormat(authorizationHeader);
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateAuthorizationFormat(final String authorizationHeader) {
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new NoSuchTokenException();
        }
    }
}