package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.vendor.medium.MediumApiService;
import org.donggle.backend.application.service.vendor.medium.dto.request.MediumPublishRequest;
import org.donggle.backend.application.service.vendor.medium.dto.request.MediumRequestBody;
import org.donggle.backend.application.service.vendor.medium.dto.request.MediumRequestHeader;
import org.donggle.backend.application.service.vendor.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.application.service.vendor.tistory.TistoryApiService;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryGetWritingResponseWrapper;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.exception.business.MediumNotConnectedException;
import org.donggle.backend.exception.business.TistoryNotConnectedException;
import org.donggle.backend.exception.business.WritingAlreadyPublishedException;
import org.donggle.backend.exception.notfound.BlogNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PublishService {
    private final BlogRepository blogRepository;
    private final WritingRepository writingRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final MediumApiService mediumApiService;
    private final TistoryApiService tistoryApiService;
    private final HtmlRenderer htmlRenderer;

    public void publishWriting(final Long memberId, final Long writingId, final PublishRequest publishRequest) {
        final Blog blog = findBlog(publishRequest);
        final Member member = findMember(memberId);
        final Writing writing = findWriting(member.getId(), writingId);

        final List<BlogWriting> publishedBlogs = blogWritingRepository.findByWritingId(writingId);
        publishedBlogs.forEach(publishedBlog -> checkWritingAlreadyPublished(publishedBlog, blog.getBlogType(), writing));

        final List<Block> blocks = writing.getBlocks();
        final String content = htmlRenderer.render(blocks);

        final BlogWriting blogWriting = switch (blog.getBlogType()) {
            case MEDIUM -> createBlogWritingAfterMediumPublish(publishRequest, member, blog, writing, content);
            case TISTORY -> createBlogWritingAfterTistoryPublish(publishRequest, member, blog, writing, content);
        };

        blogWritingRepository.save(blogWriting);
    }

    private void checkWritingAlreadyPublished(final BlogWriting publishedBlog, final BlogType blogType, final Writing writing) {
        if (publishedBlog.isSameBlogType(blogType)
                && (writing.getUpdatedAt().isBefore(publishedBlog.getPublishedAt()))) {
            throw new WritingAlreadyPublishedException(writing.getId(), blogType);
        }
    }

    private BlogWriting createBlogWritingAfterMediumPublish(final PublishRequest publishRequest, final Member member, final Blog blog, final Writing writing, final String content) {
        final MediumPublishRequest request = buildMediumRequest(member, publishRequest, writing, content);
        final MediumPublishResponse response = mediumApiService.publishContent(request);
        return new BlogWriting(blog, writing, response.getDateTime(), response.getTags());
    }

    private MediumPublishRequest buildMediumRequest(final Member member, final PublishRequest publishRequest, final Writing writing, final String content) {
        final MemberCredentials memberCredentials = findMemberCredentials(member);
        final String mediumToken = memberCredentials.getMediumToken()
                .orElseThrow(MediumNotConnectedException::new);
        final MediumRequestBody body = MediumRequestBody.builder()
                .title(writing.getTitleValue())
                .content(content)
                .contentFormat("html")
                .tags(publishRequest.tags())
                .build();
        final MediumRequestHeader header = new MediumRequestHeader(mediumToken);
        return new MediumPublishRequest(header, body);
    }

    private BlogWriting createBlogWritingAfterTistoryPublish(final PublishRequest publishRequest, final Member member, final Blog blog, final Writing writing, final String content) {
        final TistoryPublishRequest request = buildTistoryRequest(member, publishRequest, writing, content);
        final TistoryGetWritingResponseWrapper response = tistoryApiService.publishContent(request);
        return new BlogWriting(blog, writing, response.getDateTime(), response.getTags());
    }

    private TistoryPublishRequest buildTistoryRequest(final Member member, final PublishRequest publishRequest, final Writing writing, final String content) {
        final MemberCredentials memberCredentials = findMemberCredentials(member);
        final String tistoryToken = memberCredentials.getTistoryToken()
                .orElseThrow(TistoryNotConnectedException::new);
        final String tistoryBlogName = memberCredentials.getTistoryBlogName()
                .orElseThrow(TistoryNotConnectedException::new);
        return TistoryPublishRequest.builder()
                .access_token(tistoryToken)
                .blogName(tistoryBlogName)
                .output("json")
                .title(writing.getTitleValue())
                .content(content)
                .tag(String.join(",", publishRequest.tags()))
                .build();
    }

    private MemberCredentials findMemberCredentials(final Member member) {
        return memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Writing findWriting(final Long memberId, final Long writingId) {
        return writingRepository.findByMemberIdAndId(memberId, writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
    }

    private Blog findBlog(final PublishRequest publishRequest) {
        for (final BlogType blogType : BlogType.values()) {
            if (blogType.name().equals(publishRequest.publishTo())) {
                return blogRepository.findByBlogType(blogType)
                        .orElseThrow(() -> new BlogNotFoundException(publishRequest.publishTo()));
            }
        }
        throw new BlogNotFoundException(publishRequest.publishTo());
    }
}
