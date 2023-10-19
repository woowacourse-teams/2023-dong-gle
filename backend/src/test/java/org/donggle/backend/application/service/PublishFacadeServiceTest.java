package org.donggle.backend.application.service;

import jakarta.persistence.EntityManager;
import org.donggle.backend.application.service.publish.PublishFacadeService;
import org.donggle.backend.application.service.publish.PublishService;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.request.PublishWritingRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogClients;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.ui.response.PublishResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.donggle.backend.domain.blog.BlogType.MEDIUM;
import static org.donggle.backend.support.fix.WritingFixture.writing_ACTIVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PublishFacadeServiceTest {
    @InjectMocks
    private PublishFacadeService publishFacadeService;
    @Mock
    private HtmlRenderer htmlRenderer;
    @Mock
    private BlogClients blogClients;
    @Mock
    private PublishService publishService;
    @Mock
    private EntityManager entityManager;

    @Test
    @DisplayName("블로그 발행 테스트")
    void alreadyPublishedException() {
        //given
        final long memberId = 10L;
        final long writingId = 1L;
        final PublishRequest publishRequest = new PublishRequest(List.of(), "PUBLIC", "", "", "");
        final PublishWritingRequest publishWritingRequest = new PublishWritingRequest(new Blog(MEDIUM), writing_ACTIVE, "token");

        given(publishService.findPublishWriting(memberId, writingId, MEDIUM)).willReturn(publishWritingRequest);
        given(htmlRenderer.render(publishWritingRequest.writing().getBlocks())).willReturn("");
        given(blogClients.publish(publishWritingRequest.blog().getBlogType(), publishRequest, "", "token", "Title 1"))
                .willReturn(new PublishResponse(LocalDateTime.now(), List.of(), "https://donggle.blog/"));

        //when & then
        assertDoesNotThrow(() -> publishFacadeService.publishWriting(memberId, writingId, MEDIUM, publishRequest));
    }
}
