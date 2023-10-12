package org.donggle.backend.application.service.publish;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.exception.business.NotConnectedException;
import org.donggle.backend.exception.business.WritingAlreadyPublishedException;
import org.donggle.backend.exception.notfound.BlogNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.PublishResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.donggle.backend.domain.writing.BlockType.CODE_BLOCK;
import static org.donggle.backend.domain.writing.BlockType.HORIZONTAL_RULES;
import static org.donggle.backend.domain.writing.BlockType.IMAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class PublishService {
    private final BlogRepository blogRepository;
    private final WritingRepository writingRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    public PublishWritingRequest findPublishWriting(final Long memberId, final Long writingId, final BlogType blogType) {
        final Blog blog = findBlog(blogType);
        final Member member = findMember(memberId);
        final Writing writing = writingRepository.findByIdWithBlocks(writingId).orElseThrow(() -> new WritingNotFoundException(writingId));
        findStylesByNomalBlocks(writing);

        validateAuthorization(member.getId(), writing);

        final MemberCredentials memberCredentials = findMemberCredentials(member);
        final String accessToken = memberCredentials.getBlogToken(blogType).orElseThrow(() -> new NotConnectedException(blogType));
        final List<BlogWriting> publishedBlogs = blogWritingRepository.findByWritingId(writingId);

        checkWritingAlreadyPublished(publishedBlogs, blog.getBlogType(), writing);
        return new PublishWritingRequest(blog, writing, accessToken);
    }

    private void findStylesByNomalBlocks(final Writing writing) {
        final List<Block> blocks = writing.getBlocks();
        final Set<BlockType> notNormalType = Set.of(CODE_BLOCK, IMAGE, HORIZONTAL_RULES);
        final List<NormalBlock> normalBlocks = blocks.stream().filter(block -> !notNormalType.contains(block.getBlockType())).map(NormalBlock.class::cast).toList();
        writingRepository.findStylesForBlocks(normalBlocks);
    }

    public void saveProperties(final Blog blog, final Writing writing, final PublishResponse response) {
        blogWritingRepository.save(new BlogWriting(blog, writing, response.dateTime(), response.tags(), response.url()));
    }


    private MemberCredentials findMemberCredentials(final Member member) {
        return memberCredentialsRepository.findByMember(member).orElseThrow(NoSuchElementException::new);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private void checkWritingAlreadyPublished(final List<BlogWriting> publishedBlogs, final BlogType blogType, final Writing writing) {
        final boolean isAlreadyPublished = publishedBlogs.stream().anyMatch(blogWriting -> blogWriting.isSameBlogType(blogType) && writing.getUpdatedAt().isBefore(blogWriting.getPublishedAt()));

        if (isAlreadyPublished) {
            throw new WritingAlreadyPublishedException(writing.getId(), blogType);
        }
    }

    private void validateAuthorization(final Long memberId, final Writing writing) {
        if (!writing.isOwnedBy(memberId)) {
            throw new WritingNotFoundException(writing.getId());
        }
    }

    private Blog findBlog(final BlogType blogType) {
        for (final BlogType type : BlogType.values()) {
            if (type.name().equals(blogType.name())) {
                return blogRepository.findByBlogType(type).orElseThrow(() -> new BlogNotFoundException(blogType.name()));
            }
        }
        throw new BlogNotFoundException(blogType.name());
    }
}
