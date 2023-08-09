package org.donggle.backend.application.service.oauth.kakao.dto;

public record KakaoAccountResponse(
        boolean profile_nickname_needs_agreement,
        ProfileResponse profile
) {
}
