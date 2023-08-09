package org.donggle.backend.auth;

public record TokenResponse(String accessToken, String refreshToken) {
}
