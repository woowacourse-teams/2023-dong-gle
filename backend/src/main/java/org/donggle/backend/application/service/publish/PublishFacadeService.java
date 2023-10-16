package org.donggle.backend.application.service.publish;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.request.PublishWritingRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogClients;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.ui.response.PublishResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishFacadeService {
    private final PublishService publishService;
    private final HtmlRenderer htmlRenderer;
    private final BlogClients blogClients;

    public void publishWriting(final Long memberId, final Long writingId, final BlogType blogType, final PublishRequest publishRequest) {
        final PublishWritingRequest request = publishService.findPublishWriting(memberId, writingId, blogType);
        final Blog blog = request.blog();
        final Writing writing = request.writing();


        final String content = htmlRenderer.render(writing.getBlocks());

        final PublishResponse response = blogClients.publish(blogType, publishRequest, content, request.accessToken(), writing.getTitleValue());
        publishService.saveProperties(blog, writing, response);
    }
}
