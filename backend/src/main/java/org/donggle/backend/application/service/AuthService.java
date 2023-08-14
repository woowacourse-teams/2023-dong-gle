package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.auth.JwtTokenProvider;
import org.donggle.backend.auth.RefreshToken;
import org.donggle.backend.auth.exception.NoSuchTokenException;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.ui.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    public TokenResponse loginByKakao(final KakaoProfileResponse kakaoProfileResponse) {
        final Member loginMember = memberRepository.findByKakaoId(kakaoProfileResponse.id())
                .orElseGet(() -> memberRepository.save(Member.createByKakao(
                        new MemberName(kakaoProfileResponse.getNickname()),
                        kakaoProfileResponse.id())
                ));
        return createTokens(loginMember);
    }

    public TokenResponse reissueAccessTokenAndRefreshToken(final Long memberId) {
        final Member member = memberRepository.findById(memberId).
                orElseThrow(NoSuchTokenException::new);

        return createTokens(member);
    }

    private TokenResponse createTokens(final Member loginMember) {
        final String accessToken = jwtTokenProvider.createAccessToken(loginMember.getId());
        final String refreshToken = jwtTokenProvider.createRefreshToken(loginMember.getId());

        synchronizeRefreshToken(loginMember, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    public void logout(final Long memberId) {
        final Member member = memberRepository.findById(memberId).
                orElseThrow(NoSuchTokenException::new);

        tokenRepository.deleteByMemberId(member.getId());
    }

    private void synchronizeRefreshToken(final Member member, final String refreshToken) {
        tokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        token -> token.updateRefreshToken(refreshToken),
                        () -> tokenRepository.save(new RefreshToken(refreshToken, member))
                );
    }
}
