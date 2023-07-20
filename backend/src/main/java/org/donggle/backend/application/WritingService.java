package org.donggle.backend.application;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.ContentRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.Block;
import org.donggle.backend.domain.BlogWriting;
import org.donggle.backend.domain.Member;
import org.donggle.backend.domain.Writing;
import org.donggle.backend.domain.content.Content;
import org.donggle.backend.domain.parser.MarkDownParser;
import org.donggle.backend.domain.parser.MarkDownStyleParser;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.dto.WritingPropertiesResponse;
import org.donggle.backend.dto.WritingResponse;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
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
        final Writing writing = writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);

        final String content = htmlRenderer.render(blocks);

        return new WritingResponse(writing.getId(), writing.getTitle(), content);
    }

    public WritingPropertiesResponse findWritingProperties(final Long memberId, final Long writingId) {
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Writing writing = writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
        List<BlogWriting> blogWritings = blogWritingRepository.findByWritingId(writingId);

        List<String> blogNames = blogWritings.stream()
                .map(blogWriting -> blogWriting.getBlog().getName())
                .toList();

        return new WritingPropertiesResponse(writing.getCreatedAt(), writing.isPublished(), writing.getPublishedAt(), blogNames);
    }
}
