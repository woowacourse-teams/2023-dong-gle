package org.donggle.backend.application.service;

import jakarta.transaction.Transactional;
import org.donggle.backend.application.service.blog.PublishFacadeService;
import org.donggle.backend.application.service.blog.PublishService;
import org.donggle.backend.application.service.blog.PublishWritingRequest;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogClients;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.ui.response.PublishResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.donggle.backend.domain.blog.BlogType.MEDIUM;
import static org.donggle.backend.fix.WritingFixture.writing;
import static org.mockito.BDDMockito.given;

@Transactional
@SpringBootTest
class PublishFacadeServiceTest {
    @InjectMocks
    private PublishFacadeService publishFacadeService;
    @Mock
    private HtmlRenderer htmlRenderer;
    @Mock
    private BlogClients blogClients;
    @Mock
    private PublishService publishService;

    @Test
    @DisplayName("블로그 발생 테스트")
    void alreadyPublishedException() {
        //given
        final long memberId = 10L;
        final long writingId = 1L;
        final PublishRequest publishRequest = new PublishRequest(List.of(), "PUBLIC", "", "", "");
        final PublishWritingRequest publishWritingRequest = new PublishWritingRequest(new Blog(MEDIUM), writing, "token");
        given(publishService.findPublishWriting(memberId, writingId, MEDIUM)).willReturn(publishWritingRequest);
        given(blogClients.publish(publishWritingRequest.blog().getBlogType(), publishRequest, "", "token", "잉표"))
                .willReturn(new PublishResponse(LocalDateTime.now(), List.of(), "https://donggle.blog/"));

        //when
        //then
        Assertions.assertDoesNotThrow(() -> publishFacadeService.publishWriting(memberId, writingId, MEDIUM, publishRequest));
    }
}
