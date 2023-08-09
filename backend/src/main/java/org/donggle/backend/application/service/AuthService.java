package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.auth.JwtTokenProvider;
import org.donggle.backend.auth.JwtTokenService;
import org.donggle.backend.auth.TokenResponse;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;


    public TokenResponse loginByKakao(final KakaoProfileResponse kakaoProfileResponse) {
        final Member loginMember = memberRepository.findByKakaoId(kakaoProfileResponse.id())
                .orElseGet(() -> memberRepository.save(Member.createByKakao(
                        new MemberName(kakaoProfileResponse.getNickname()),
                        kakaoProfileResponse.id())
                ));
        final String accessToken = jwtTokenProvider.createAccessToken(loginMember.getId());
        final String refreshToken = jwtTokenProvider.createRefreshToken(loginMember.getId());
        jwtTokenService.synchronizeRefreshToken(loginMember, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
