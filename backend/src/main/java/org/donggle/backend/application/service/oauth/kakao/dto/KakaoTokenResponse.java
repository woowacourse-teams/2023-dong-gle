package org.donggle.backend.application.service.oauth.kakao.dto;

public record KakaoTokenResponse(
        String token_type,
        String access_token,
        Long expires_in,
        String refresh_token,
        Long refresh_token_expires_in,
        String scope
) {
}
