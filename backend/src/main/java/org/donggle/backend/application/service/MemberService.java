package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.oauth.kakao.dto.KakaoProfileResponse;
import org.donggle.backend.domain.member.Member;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void loginByKakao(final KakaoProfileResponse kakaoProfileResponse) {
        final Member loginMember = memberRepository.findByKakaoId(kakaoProfileResponse.id())
                .orElseGet(() -> memberRepository.save(Member.createByKakao(kakaoProfileResponse)));
    }
}
