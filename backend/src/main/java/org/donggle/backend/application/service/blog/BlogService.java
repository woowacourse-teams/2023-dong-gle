package org.donggle.backend.application.service.blog;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogClients;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.exception.business.WritingAlreadyPublishedException;
import org.donggle.backend.ui.response.PublishResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final PublishService publishService;
    private final HtmlRenderer htmlRenderer;
    private final BlogClients blogClients;

    public void publishWriting(final Long memberId, final Long writingId, final PublishRequest publishRequest) {
        final BlogType blogType = BlogType.from(publishRequest.publishTo());
        final List<String> tags = publishRequest.tags();

        final PublishWritingRequest request = publishService.findPublishWriting(memberId, writingId, blogType);
        final Blog blog = request.blog();
        final Writing writing = request.writing();
        checkWritingAlreadyPublished(request.publishedBlogs(), blog.getBlogType(), writing);
        final String content = htmlRenderer.render(writing.getBlocks());

        final PublishResponse response = blogClients.publish(blogType, tags, content, request.accessToken(), writing.getTitleValue());
        publishService.saveProperties(blog, writing, response);
    }

    private void checkWritingAlreadyPublished(final List<BlogWriting> publishedBlogs, final BlogType blogType, final Writing writing) {
        final boolean isAlreadyPublished = publishedBlogs.stream()
                .anyMatch(blogWriting -> blogWriting.isSameBlogType(blogType)
                        && writing.getUpdatedAt().isBefore(blogWriting.getPublishedAt()));

        if (isAlreadyPublished) {
            throw new WritingAlreadyPublishedException(writing.getId(), blogType);
        }
    }
}
