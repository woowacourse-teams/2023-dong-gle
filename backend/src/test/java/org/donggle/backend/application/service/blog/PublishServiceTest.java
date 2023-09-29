package org.donggle.backend.application.service.blog;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.TistoryNotConnectedException;
import org.donggle.backend.exception.business.WritingAlreadyPublishedException;
import org.donggle.backend.exception.notfound.BlogNotFoundException;
import org.donggle.backend.ui.response.PublishResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.donggle.backend.fix.MemberFixture.beaver;
import static org.donggle.backend.fix.WritingFixture.writing_ACTIVE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PublishServiceTest {
    @InjectMocks
    private PublishService publishService;
    @Mock
    private BlogRepository blogRepository;
    @Mock
    private WritingRepository writingRepository;
    @Mock
    private BlogWritingRepository blogWritingRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberCredentialsRepository memberCredentialsRepository;

    @Test
    @DisplayName("발행 블로그에 대한 정보를 불러오는 테스트")
    void findPublishWriting() {
        // given
        final Long memberId = 10L;
        final Long writingId = 1L;
        final BlogType blogType = BlogType.MEDIUM;

        final Blog blog = mock(Blog.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        given(blogRepository.findByBlogType(blogType)).willReturn(Optional.of(blog));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver));
        given(writingRepository.findByIdWithBlocks(writingId)).willReturn(Optional.of(writing_ACTIVE));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver)).willReturn(Optional.of(memberCredentials));
        given(memberCredentials.getBlogToken(blogType)).willReturn(Optional.of("token"));

        // when
        final PublishWritingRequest result = publishService.findPublishWriting(memberId, writingId, blogType);

        // then
        assertThat(result.writing()).isEqualTo(writing_ACTIVE);
        assertThat(result.accessToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("발행 정보를 불러올때 블로그에 맞는 토큰이 없을 때 에러")
    void findPublishWriting_not_found_tistory_token() {
        // given
        final Long memberId = 10L;
        final Long writingId = 1L;
        final BlogType blogType = BlogType.MEDIUM;

        final Blog blog = mock(Blog.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        given(blogRepository.findByBlogType(blogType)).willReturn(Optional.of(blog));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver));
        given(writingRepository.findByIdWithBlocks(writingId)).willReturn(Optional.of(writing_ACTIVE));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver)).willReturn(Optional.of(memberCredentials));
        given(memberCredentials.getBlogToken(blogType)).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> publishService.findPublishWriting(memberId, writingId, blogType)
        ).isInstanceOf(TistoryNotConnectedException.class);
    }

    @Test
    @DisplayName("지원되지 않는 blog요청 시 에러")
    void findPublishWriting_not_support_blog() {
        // given
        final Long memberId = 10L;
        final Long writingId = 1L;
        final BlogType blogType = BlogType.MEDIUM;


        given(blogRepository.findByBlogType(blogType)).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> publishService.findPublishWriting(memberId, writingId, blogType)
        ).isInstanceOf(BlogNotFoundException.class);
    }

    @Test
    @DisplayName("해당 글을 블로그에 한번 더 올리면 에러")
    void findPublishWriting_AlreadyPublishedException() {
        // given
        final Long memberId = 10L;
        final Long writingId = 1L;
        final BlogType blogType = BlogType.MEDIUM;

        final Blog blog = new Blog(blogType);
        final BlogWriting blogWriting = new BlogWriting(blog, writing_ACTIVE, LocalDateTime.now(), List.of("tag1"), "url");
        final MemberCredentials memberCredentials = MemberCredentials.basic(beaver);
        memberCredentials.updateMediumToken("token");
        given(blogRepository.findByBlogType(blogType)).willReturn(Optional.of(blog));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver));
        given(writingRepository.findByIdWithBlocks(writingId)).willReturn(Optional.of(writing_ACTIVE));
        given(memberCredentialsRepository.findMemberCredentialsByMember(beaver)).willReturn(Optional.of(memberCredentials));
        given(blogWritingRepository.findByWritingId(writingId)).willReturn(List.of(blogWriting));

        //when
        //then
        assertThatThrownBy(() -> publishService.findPublishWriting(memberId, writingId, blogType))
                .isInstanceOf(WritingAlreadyPublishedException.class);
    }


    @Test
    @DisplayName("발행한 정보를 저장하는 테스트")
    void saveProperties() {
        //given
        final Blog blog = mock(Blog.class);
        final Writing writing = mock(Writing.class);
        final PublishResponse response = mock(PublishResponse.class);

        given(response.dateTime()).willReturn(LocalDateTime.now());
        given(response.tags()).willReturn(List.of("tag1", "tag2"));
        given(response.url()).willReturn("someUrl");

        //when
        publishService.saveProperties(blog, writing, response);

        //then
        then(blogWritingRepository).should(times(1)).save(any(BlogWriting.class));
    }
}