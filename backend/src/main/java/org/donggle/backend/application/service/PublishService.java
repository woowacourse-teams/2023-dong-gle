package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.medium.MediumApiService;
import org.donggle.backend.application.service.medium.dto.request.MediumPublishRequest;
import org.donggle.backend.application.service.medium.dto.response.MediumPublishResponse;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.tistory.TistoryApiService;
import org.donggle.backend.application.service.tistory.request.TistoryPublishRequest;
import org.donggle.backend.application.service.tistory.response.TistoryPublishWritingResponse;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
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
    private final MediumApiService mediumApiService;
    private final TistoryApiService tistoryApiService;

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
                        .title(writing.getTitleValue())
                        .content(content)
                        .contentFormat("html")
                        .build();
                final MediumPublishResponse response = mediumApiService.publishContent(request);
                final BlogWriting blogWriting = new BlogWriting(blog, writing, response.data().getPublishedAt());
                blogWritingRepository.save(blogWriting);
            }
            case TISTORY -> {
                final TistoryPublishRequest request = TistoryPublishRequest.builder()
                        .access_token("tistoryToken")
                        .blogName("jeoninpyo726")
                        .title(writing.getTitle())
                        .content(content)
                        .build();
                final TistoryPublishWritingResponse response = tistoryApiService.publishContent(request);
                final BlogWriting blogWriting = new BlogWriting(blog, writing, response.tistory().item().getDateTime());
                blogWritingRepository.save(blogWriting);
            }
        }
    }
}
