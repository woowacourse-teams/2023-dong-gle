package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.PublishResponse;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
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
    private final BlockRepository blockRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final BlogClients blogClients;

    public void publishWriting(final Long memberId, final Long writingId, final PublishRequest publishRequest) {
        final Blog blog = findBlog(publishRequest);
        final Member member = findMember(memberId);
        final Writing writing = findWriting(member.getId(), writingId);
        final BlogType blogType = BlogType.from(publishRequest.publishTo());
        final List<String> tags = publishRequest.tags();
        final MemberCredentials memberCredentials = findMemberCredentials(member);
        final String accessToken = memberCredentials.getBlogToken(blogType)
                .orElseThrow(TistoryNotConnectedException::new);
        final List<BlogWriting> publishedBlogs = blogWritingRepository.findByWritingId(writingId);
        publishedBlogs.forEach(publishedBlog -> checkWritingAlreadyPublished(publishedBlog, blog.getBlogType(), writing));
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);
        final String content = new HtmlRenderer(new HtmlStyleRenderer()).render(blocks);
        final PublishResponse response = blogClients.publish(blogType, tags, content, accessToken, writing.getTitleValue());
        blogWritingRepository.save(new BlogWriting(blog, writing, response.bateTime(), response.tags()));
    }

    private void checkWritingAlreadyPublished(final BlogWriting publishedBlog, final BlogType blogType, final Writing writing) {
        if (publishedBlog.isSameBlogType(blogType)
                && (writing.getUpdatedAt().isBefore(publishedBlog.getPublishedAt()))) {
            throw new WritingAlreadyPublishedException(writing.getId(), blogType);
        }
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
