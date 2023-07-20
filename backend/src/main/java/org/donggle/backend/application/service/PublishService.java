package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.medium.MediumApiService;
import org.donggle.backend.application.service.medium.dto.MediumPublishRequest;
import org.donggle.backend.application.service.medium.dto.MediumPublishResponse;
import org.donggle.backend.domain.Block;
import org.donggle.backend.domain.Blog;
import org.donggle.backend.domain.BlogType;
import org.donggle.backend.domain.BlogWriting;
import org.donggle.backend.domain.Writing;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.dto.PublishRequest;
import org.donggle.backend.exception.notfound.BlogNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublishService {
    private final BlogRepository blogRepository;
    private final WritingRepository writingRepository;
    private final BlockRepository blockRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MediumApiService mediumApiService;

    public void publishWriting(final Long memberId, final Long writingId, final PublishRequest request) {
        final String blogName = request.publishTo();
        System.out.println("blogName = " + blogName);
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Blog blog = blogRepository.findByName(blogName)
                .orElseThrow(() -> new BlogNotFoundException(blogName));
        final Writing writing = writingRepository.findById(writingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);
        final HtmlRenderer htmlRenderer = new HtmlRenderer(new HtmlStyleRenderer());
        final String content = htmlRenderer.render(blocks);

        switch (BlogType.valueOf(blogName)) {
            case MEDIUM:
                // TODO : Medium API 호출
                final MediumPublishRequest publishRequest = MediumPublishRequest.builder()
                        .title(writing.getTitle())
                        .content(content)
                        .contentFormat("html")
                        .publishStatus("draft")
                        .build();
                final MediumPublishResponse mediumPublishResponse = mediumApiService.publishContent(publishRequest);
                final BlogWriting blogWriting = new BlogWriting(blog, writing, mediumPublishResponse.data().getPublishedAt());
                blogWritingRepository.save(blogWriting);
                break;
            case TISTORY:
                // TODO : Tistory API 호출
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 블로그입니다.");
        }
    }
}
