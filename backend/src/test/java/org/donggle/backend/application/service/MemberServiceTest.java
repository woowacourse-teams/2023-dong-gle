package org.donggle.backend.application.service;

import jakarta.persistence.EntityManager;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.member.MemberService;
import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.ui.response.MemberPageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.TestFixtures.BASIC_CATEGORY;
import static org.donggle.backend.TestFixtures.GENERAL_WRITING;
import static org.donggle.backend.TestFixtures.MEMBER;
import static org.donggle.backend.TestFixtures.MEMBER_CREDENTIALS;
import static org.donggle.backend.TestFixtures.REFRESH_TOKEN;
import static org.donggle.backend.domain.oauth.SocialType.KAKAO;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogWritingRepository blogWritingRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private MemberCredentialsRepository memberCredentialsRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원 페이지 조회 - 아무것도 연결되어있지 않은 회원")
    void findMemberPage() {
        //given
        final Member member = memberRepository.save(Member.of(new MemberName("토리"), 1234L, KAKAO));
        final MemberCredentials savedMemberCredentials = memberCredentialsRepository.save(MemberCredentials.basic(member));
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(member.getId());

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(member.getId()),
                () -> assertThat(memberPage.name()).isEqualTo(member.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isFalse(),
                () -> assertThat(memberPage.tistory().blogName()).isNull(),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isFalse()
        );
    }

    @Test
    @DisplayName("회원 페이지 조회 - 티스토리 연결된 회원")
    void findMemberPage2() {
        //given
        final Member member = memberRepository.findById(1L).get();
        final MemberCredentials savedMemberCredentials = memberCredentialsRepository.findById(1L).get();
        savedMemberCredentials.updateTistory("test", "test");
        //when
        final MemberPageResponse memberPage = memberService.findMemberPage(member.getId());

        //then
        assertAll(
                () -> assertThat(memberPage.id()).isEqualTo(member.getId()),
                () -> assertThat(memberPage.name()).isEqualTo(member.getMemberName().getName()),
                () -> assertThat(memberPage.tistory().isConnected()).isTrue(),
                () -> assertThat(memberPage.tistory().blogName()).isEqualTo("test"),
                () -> assertThat(memberPage.notion().isConnected()).isFalse(),
                () -> assertThat(memberPage.medium().isConnected()).isTrue()
        );
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void unregisterMember() {
        // given
        final Member member = memberRepository.save(MEMBER);
        final MemberCredentials memberCredentials = memberCredentialsRepository.save(MEMBER_CREDENTIALS);
        final Category basicCategory = categoryRepository.save(BASIC_CATEGORY);
        final Writing writing = writingRepository.save(GENERAL_WRITING);
        final Blog blog = blogRepository.findByBlogType(BlogType.TISTORY).orElseThrow();
        final BlogWriting blogWriting = new BlogWriting(
                blog,
                GENERAL_WRITING,
                LocalDateTime.now(),
                List.of("태그1", "태그2"),
                "www.dongle.blog"
        );
        final BlogWriting savedBlogWriting = blogWritingRepository.save(blogWriting);
        final RefreshToken refreshToken = tokenRepository.save(REFRESH_TOKEN);

        // when
        memberService.deleteMember(member.getId());
        em.flush();
        em.clear();

        // then
        assertAll(
                () -> assertThat(writingRepository.findAllByMember(member)).isEmpty(),
                () -> assertThat(blogWritingRepository.findById(savedBlogWriting.getId())).isEmpty(),
                () -> assertThat(categoryRepository.findById(basicCategory.getId())).isEmpty(),
                () -> assertThat(tokenRepository.findById(refreshToken.getId())).isEmpty(),
                () -> assertThat(memberCredentialsRepository.findById(memberCredentials.getId())).isEmpty(),
                () -> assertThat(memberRepository.findById(member.getId())).isEmpty()
        );
    }
}
