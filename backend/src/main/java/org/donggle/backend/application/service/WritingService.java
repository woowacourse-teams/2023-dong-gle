package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.ContentRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.domain.writing.Block;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.PublishedDetailResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WritingService {
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final BlockRepository blockRepository;
    private final WritingRepository writingRepository;
    private final BlogWritingRepository blogWritingRepository;

    @Transactional
    public Long uploadMarkDownFile(final Long memberId, final String originalFilename, final String totalText) {
        final MarkDownParser markDownParser = new MarkDownParser(new MarkDownStyleParser());
        // TODO : authentication 후 member 객체 가져오도록 수정
        final Member member = memberRepository.save(new Member("동그리"));
        final Writing writing = new Writing(member, originalFilename);

        writingRepository.save(writing);

        //TODO : CASCADE 추가
        final List<Content> contents = markDownParser.parse(totalText);
        for (final Content content : contents) {
            final Content savedContent = contentRepository.save(content);
            blockRepository.save(new Block(writing, savedContent));
        }

        return writing.getId();
    }

    public WritingResponse findWriting(final Long memberId, final Long writingId) {
        final HtmlRenderer htmlRenderer = new HtmlRenderer(new HtmlStyleRenderer());
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Writing writing = findWriting(writingId);
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);

        final String content = htmlRenderer.render(blocks);

        return new WritingResponse(writing.getId(), writing.getTitle(), content);
    }

    private Writing findWriting(final Long writingId) {
        return writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
    }

    public WritingPropertiesResponse findWritingProperties(final Long memberId, final Long writingId) {
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Writing writing = findWriting(writingId);
        final List<BlogWriting> blogWritings = blogWritingRepository.findByWritingId(writingId);
        final List<PublishedDetailResponse> publishedTos = blogWritings.stream()
                .map(blogWriting -> new PublishedDetailResponse(
                        blogWriting.getBlogName(),
                        blogWriting.getPublishedAt())
                )
                .toList();

        return new WritingPropertiesResponse(writing.getCreatedAt(), publishedTos);
    }
}
