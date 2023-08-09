package org.donggle.backend.application.service.oauth.kakao.dto;

public record KakaoProfileResponse(
        Long id,
        String connected_at,
        KakaoAccountResponse kakao_account
) {
    public String getNickname() {
        return this.kakao_account.profile().nickname();
    }
}
