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
    private final MediumApiService mediumApiService;

    public void publishWriting(final Long memberId, final Long writingId, final PublishRequest publishRequest) {
        final String blogName = publishRequest.publishTo();
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가

        final Blog blog = blogRepository.findByBlogType(BlogType.valueOf(blogName))
                .orElseThrow(() -> new BlogNotFoundException(blogName));

        final Writing writing = writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));

        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);

        final String content = new HtmlRenderer(new HtmlStyleRenderer()).render(blocks);

        switch (blog.getBlogType()) {
            case MEDIUM -> {
                final MediumPublishRequest request = MediumPublishRequest.builder()
                        .title(writing.getTitle())
                        .content(content)
                        .contentFormat("html")
                        .publishStatus("draft")
                        .build();
                final MediumPublishResponse response = mediumApiService.publishContent(request);
                final BlogWriting blogWriting = new BlogWriting(blog, writing, response.data().getPublishedAt());
                blogWritingRepository.save(blogWriting);
            }
            case TISTORY -> {
                //TODO : TISTORY API 연동
            }
        }
    }
}
