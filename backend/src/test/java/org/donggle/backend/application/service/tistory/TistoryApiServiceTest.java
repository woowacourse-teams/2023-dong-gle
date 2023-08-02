package org.donggle.backend.application.service.tistory;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.service.vendor.tistory.TistoryApiService;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishPropertyRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryPublishWritingResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TistoryApiServiceTest {
    @Test
    @Disabled
    @DisplayName("블로그 발행이 정상적으로 되는지 테스트")
    void publishContent() {
        //given
        final TistoryApiService tistoryApiService = new TistoryApiService();
        final TistoryPublishRequest request = TistoryPublishRequest.builder()
                .access_token("tistoryToken")
                .blogName("jeoninpyo726")
                .title("아이언제로")
                .output("json")
                .content("""
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <title>id 없는 HTML 예시</title>
                        </head>
                        <body>
                            <h1>제목</h1>
                            <p>이것은 단락입니다.</p>
                            <ul>
                                <li>리스트 아이템 1</li>
                                <li>리스트 아이템 2</li>
                                <li>리스트 아이템 3</li>
                            </ul>
                            <div>
                                <p>이것은 div 안에 있는 단락입니다.</p>
                            </div>
                            <p>또 다른 단락입니다.</p>
                        </body>
                        </html>
                        """)
                .build();

        //when
        final TistoryPublishWritingResponse result = tistoryApiService.publishContent(request);

        //then
        assertThat(result.tistory().status()).isEqualTo(200);
    }

    @Test
    @Disabled
    @DisplayName("발행된 글의대한 정보 조회 테스트")
    void findPublishProperty() {
        //given
        final TistoryApiService tistoryApiService = new TistoryApiService();
        final TistoryPublishPropertyRequest tistoryPublishPropertyRequest = TistoryPublishPropertyRequest.builder()
                .access_token("tistoryToken")
                .postId(32L)
                .blogName("jeoninpyo726")
                .build();

        //when
        final TistoryPublishWritingResponse publishProperty = tistoryApiService.findPublishProperty(tistoryPublishPropertyRequest);

        //then
        assertThat(publishProperty.tistory().status()).isEqualTo(200);
    }
}
