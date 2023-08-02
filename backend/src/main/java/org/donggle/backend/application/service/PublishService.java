package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.medium.MediumApiService;
import org.donggle.backend.application.service.medium.dto.request.MediumPublishRequest;
import org.donggle.backend.application.service.medium.dto.request.MediumRequestBody;
import org.donggle.backend.application.service.medium.dto.request.MediumRequestHeader;
import org.donggle.backend.application.service.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.tistory.TistoryApiService;
import org.donggle.backend.application.service.tistory.request.TistoryPublishRequest;
import org.donggle.backend.application.service.tistory.response.TistoryPublishWritingResponse;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.domain.writing.Block;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.notfound.BlogNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublishService {
    private final BlogRepository blogRepository;
    private final WritingRepository writingRepository;
    private final BlockRepository blockRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final MediumApiService mediumApiService = new MediumApiService();
    private final TistoryApiService tistoryApiService = new TistoryApiService();


    public void publishWriting(final Long memberId, final Long writingId, final PublishRequest publishRequest) {
        final String blogName = publishRequest.publishTo();
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Member member = memberRepository.findById(memberId).orElseThrow();
        final Blog blog = blogRepository.findByBlogType(BlogType.valueOf(blogName))
                .orElseThrow(() -> new BlogNotFoundException(blogName));
        final Writing writing = writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);
        final String content = new HtmlRenderer(new HtmlStyleRenderer()).render(blocks);

        final BlogWriting blogWriting = switch (blog.getBlogType()) {
            case MEDIUM -> createBlogWritingAfterMediumPublish(publishRequest, member, blog, writing, content);
            case TISTORY -> createBlogWritingAfterTistoryPublish(publishRequest, member, blog, writing, content);
        };

        blogWritingRepository.save(blogWriting);
    }

    private BlogWriting createBlogWritingAfterMediumPublish(final PublishRequest publishRequest, final Member member, final Blog blog, final Writing writing, final String content) {
        final MediumPublishRequest request = buildMediumRequest(member, publishRequest, writing, content);
        final MediumPublishResponse response = mediumApiService.publishContent(request);
        return new BlogWriting(blog, writing, response.data().getPublishedAt());
    }

    private BlogWriting createBlogWritingAfterTistoryPublish(final PublishRequest publishRequest, final Member member, final Blog blog, final Writing writing, final String content) {
        final TistoryPublishRequest request = buildTistoryRequest(member, publishRequest, writing, content);
        final TistoryPublishWritingResponse response = tistoryApiService.publishContent(request);
        return new BlogWriting(blog, writing, response.tistory().item().getDateTime());
    }

    private TistoryPublishRequest buildTistoryRequest(final Member member, final PublishRequest publishRequest, final Writing writing, final String content) {
        //TODO TistoryToken 못찾은 예외 발생시키기
        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member).orElseThrow();
        //TODO TistoryBlogName 못찾은 예외 발생시키기
        final String tistoryToken = memberCredentials.getTistoryToken();
        final String tistoryBlogName = memberCredentials.getTistoryBlogName();
        return TistoryPublishRequest.builder()
                .access_token(tistoryToken)
                .blogName(tistoryBlogName)
                .output("json")
                .title(writing.getTitleValue())
                .content(content)
                .tag(String.join(",", publishRequest.tags()))
                .build();
    }

    private MediumPublishRequest buildMediumRequest(final Member member, final PublishRequest publishRequest, final Writing writing, final String content) {
        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member).orElseThrow();
        final String mediumToken = memberCredentials.getMediumToken();
        final MediumRequestBody body = MediumRequestBody.builder()
                .title(writing.getTitleValue())
                .content(content)
                .contentFormat("html")
                .tags(publishRequest.tags())
                .build();
        final MediumRequestHeader header = new MediumRequestHeader(mediumToken);
        return new MediumPublishRequest(header, body);
    }
}
