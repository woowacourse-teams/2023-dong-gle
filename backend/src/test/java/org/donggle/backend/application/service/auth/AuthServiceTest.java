//package org.donggle.backend.application.service.auth;
//
//import org.assertj.core.api.Assertions;
//import org.donggle.backend.application.fake.FakeMemberRepository;
//import org.donggle.backend.application.repository.CategoryRepository;
//import org.donggle.backend.application.repository.MemberCredentialsRepository;
//import org.donggle.backend.application.repository.MemberRepository;
//import org.donggle.backend.application.repository.TokenRepository;
//import org.donggle.backend.domain.auth.JwtTokenProvider;
//import org.donggle.backend.domain.member.Member;
//import org.donggle.backend.domain.member.MemberName;
//import org.donggle.backend.domain.oauth.SocialType;
//import org.donggle.backend.exception.business.DuplicatedMemberException;
//import org.donggle.backend.infrastructure.oauth.kakao.dto.response.UserInfo;
//import org.donggle.backend.ui.response.TokenResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class AuthServiceTest {
//    @Mock
//    private MemberRepository memberRepository;
//    @Mock
//    private JwtTokenProvider jwtTokenProvider;
//    @Mock
//    private TokenRepository tokenRepository;
//    @Mock
//    private MemberCredentialsRepository memberCredentialsRepository;
//    @Mock
//    private CategoryRepository categoryRepository;
//    @InjectMocks
//    private AuthService authService;
//
//
//    @BeforeEach
//    void setup() {
//        memberRepository = new FakeMemberRepository();
//        carRepository = new FakeCarRepository();
//        memberCarRepository = new FakeMemberCarRepository();
//        carService = new CarService(
//                memberRepository,
//                carRepository,
//                memberCarRepository
//        );
//    @Test
//    @DisplayName("로그인 테스트")
//    void login() {
//        // given
//        final Long memberId = 1L;
//        final SocialType socialType = KAKAO;
//        final UserInfo userInfo = new UserInfo(1L, KAKAO, "홍길동");
//        final Member member = Member.of(new MemberName("홍길동"), 1L, KAKAO);
//        given(memberRepository.findBySocialIdAndSocialType(anyLong(), any(SocialType.class)))
//                .willReturn(Optional.of(member));
//        given(jwtTokenProvider.createAccessToken(memberId))
//                .willReturn("accessToken");
//        given(jwtTokenProvider.createRefreshToken(memberId))
//                .willReturn("refreshToken");
//        // when
//        final TokenResponse result = authService.login(userInfo, socialType);
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.accessToken()).isEqualTo("accessToken");
//        assertThat(result.refreshToken()).isEqualTo("refreshToken");
//    }
//
//    //private Member initializeMember(final UserInfo userInfo) {
////        Member member = userInfo.toMember();
////        final Category basicCategory = Category.basic(member);
////        final MemberCredentials basic = MemberCredentials.basic(member);
////        try {
////            member = memberRepository.save(member);
////            categoryRepository.save(basicCategory);
////            memberCredentialsRepository.save(basic);
////        } catch (final DuplicateKeyException e) {
////            throw new DuplicatedMemberException(userInfo.socialType().name());
////        }
////        return member;
////    }
//    @Test
//    @DisplayName("회원가입 테스트")
//    void registry() {
//        // given
//        final Long memberId = 1L;
//        final SocialType socialType = KAKAO;
//        final UserInfo userInfo = new UserInfo(1L, KAKAO, "홍길동");
//        final Member member = Member.of(new MemberName("홍길동"), 1L, KAKAO);
//        given(memberRepository.findBySocialIdAndSocialType(memberId, any(SocialType.class)))
//                .willReturn(Optional.empty());
//        given(memberRepository.save(member)).willThrow(new DuplicatedMemberException("기본"));
//        // when
//        Assertions.assertThatThrownBy(
//                () -> authService.login(userInfo, socialType)
//        ).isInstanceOf(DuplicatedMemberException.class);
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.accessToken()).isEqualTo("accessToken");
//        assertThat(result.refreshToken()).isEqualTo("refreshToken");
//    }
//
//    @Test
//    void logout() {
//    }
//
//    @Test
//    void testReissueAccessTokenAndRefreshToken() {
//        // given
//        final Long memberId = 1L;
//        final Member member = mock(Member.class);
//
//        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
//        given(jwtTokenProvider.createAccessToken(memberId)).willReturn("newAccessToken");
//        given(jwtTokenProvider.createRefreshToken(memberId)).willReturn("newRefreshToken");
//
//        // when
//        final TokenResponse result = authService.reissueAccessTokenAndRefreshToken(memberId);
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.accessToken()).isEqualTo("newAccessToken");
//        assertThat(result.refreshToken()).isEqualTo("newRefreshToken");
//    }
//}