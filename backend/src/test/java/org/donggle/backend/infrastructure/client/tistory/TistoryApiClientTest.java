package org.donggle.backend.infrastructure.client.tistory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.business.InvalidPublishRequestException;
import org.donggle.backend.ui.response.PublishResponse;
import org.donggle.backend.ui.response.TistoryCategoryListResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TistoryApiClientTest {

    private MemberRepository memberRepository;
    private MemberCredentialsRepository memberCredentialsRepository;
    private MockWebServer mockWebServer;
    private TistoryApiClient tistoryApiClient;

    @BeforeEach
    void setUp() throws IOException {
        memberRepository = Mockito.mock(MemberRepository.class);
        memberCredentialsRepository = Mockito.mock(MemberCredentialsRepository.class);
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        final String mockServerUrl = mockWebServer.url("/").toString();
        tistoryApiClient = new TistoryApiClient(memberRepository, memberCredentialsRepository, WebClient.create(mockServerUrl));
    }

    @AfterEach
    void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("tistory에 공개발행 테스트")
    void publish_PUBLIC() {
        // given
        final String accessToken = "testToken";
        final String content = "<p>Test Content</p>";
        final LocalDateTime dateTime = LocalDateTime.parse("2023-06-01 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        final PublishRequest publishRequest = new PublishRequest(List.of("tag1", "tag2"), "PUBLIC", "", "", "");
        final PublishResponse expectedResponse = PublishResponse.builder().dateTime(dateTime).tags(List.of("open", "api")).url("http://sampleUrl.tistory.com/74").build();

        final String titleValue = "Test Title";
        final String defaultBlogName = """
                {
                   "tistory": {
                     "status": "200",
                     "item": {
                       "id": "blog_oauth_test@daum.net",
                       "userId": "12345",
                       "blogs": [
                         {
                           "name": "oauth-test",
                           "default": "Y",
                           "blogIconUrl": "https://blog_icon_url",
                           "faviconUrl": "https://favicon_url",
                           "profileThumbnailImageUrl": "https://profile_image",
                           "profileImageUrl": "https://profile_image",
                           "role": "소유자",
                           "blogId": "123",
                           "statistics": {
                             "post": "182",
                             "comment": "146",
                             "trackback": "0",
                             "guestbook": "39",
                             "invitation": "0"
                           }
                         }
                       ]
                     }
                   }
                 }
                """;
        final String publishResponseBody = """
                {
                    "tistory": {
                        "status": "200",
                        "postId": "74",
                        "url": "http://sampleUrl.tistory.com/74"
                    }
                }
                """;

        final String publishPropertyResponseBody = """
                {
                    "tistory":{
                        "status":"200",
                        "item":{
                            "url":"http://oauth.tistory.com",
                            "postUrl":"http://sampleUrl.tistory.com/74",
                            "tags":{
                                "tag":["open", "api"]
                            },
                            "date":"2023-06-01 12:30:00"
                        }
                    }
                }
                """;


        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findByMember(any(Member.class))).willReturn(Optional.of(memberCredentials));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishResponseBody).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishPropertyResponseBody).addHeader("Content-Type", "application/json"));

        // when
        final PublishResponse response = tistoryApiClient.publish(accessToken, content, publishRequest, titleValue);

        // then
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("tistory에 보호 발행 테스트")
    void publish_PROTECT() {
        // given
        final String accessToken = "testToken";
        final String content = "<p>Test Content</p>";
        final LocalDateTime dateTime = LocalDateTime.parse("2023-06-01 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        final PublishRequest publishRequest = new PublishRequest(List.of("tag1", "tag2"), "PROTECT", "", "", "");
        final PublishResponse expectedResponse = PublishResponse.builder().dateTime(dateTime).tags(List.of("open", "api")).url("http://sampleUrl.tistory.com/74").build();

        final String titleValue = "Test Title";
        final String defaultBlogName = """
                {
                   "tistory": {
                     "status": "200",
                     "item": {
                       "id": "blog_oauth_test@daum.net",
                       "userId": "12345",
                       "blogs": [
                         {
                           "name": "oauth-test",
                           "default": "Y",
                           "blogIconUrl": "https://blog_icon_url",
                           "faviconUrl": "https://favicon_url",
                           "profileThumbnailImageUrl": "https://profile_image",
                           "profileImageUrl": "https://profile_image",
                           "role": "소유자",
                           "blogId": "123",
                           "statistics": {
                             "post": "182",
                             "comment": "146",
                             "trackback": "0",
                             "guestbook": "39",
                             "invitation": "0"
                           }
                         }
                       ]
                     }
                   }
                 }
                """;
        final String publishResponseBody = """
                {
                    "tistory": {
                        "status": "200",
                        "postId": "74",
                        "url": "http://sampleUrl.tistory.com/74"
                    }
                }
                """;

        final String publishPropertyResponseBody = """
                {
                    "tistory":{
                        "status":"200",
                        "item":{
                            "url":"http://oauth.tistory.com",
                            "postUrl":"http://sampleUrl.tistory.com/74",
                            "tags":{
                                "tag":["open", "api"]
                            },
                            "date":"2023-06-01 12:30:00"
                        }
                    }
                }
                """;


        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findByMember(any(Member.class))).willReturn(Optional.of(memberCredentials));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishResponseBody).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishPropertyResponseBody).addHeader("Content-Type", "application/json"));

        // when
        final PublishResponse response = tistoryApiClient.publish(accessToken, content, publishRequest, titleValue);

        // then
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("tistory에 발행할 때 발행시간이 과거일때 예외")
    void publishTime_fail_timeIsInPast() {
        // given
        final String accessToken = "testToken";
        final String content = "<p>Test Content</p>";
        final PublishRequest publishRequest = new PublishRequest(List.of("tag1", "tag2"), "PUBLIC", "", "", "2001-06-01 12:30:00.000");

        final String titleValue = "Test Title";
        final String defaultBlogName = """
                {
                   "tistory": {
                     "status": "200",
                     "item": {
                       "id": "blog_oauth_test@daum.net",
                       "userId": "12345",
                       "blogs": [
                         {
                           "name": "oauth-test",
                           "default": "Y",
                           "blogIconUrl": "https://blog_icon_url",
                           "faviconUrl": "https://favicon_url",
                           "profileThumbnailImageUrl": "https://profile_image",
                           "profileImageUrl": "https://profile_image",
                           "role": "소유자",
                           "blogId": "123",
                           "statistics": {
                             "post": "182",
                             "comment": "146",
                             "trackback": "0",
                             "guestbook": "39",
                             "invitation": "0"
                           }
                         }
                       ]
                     }
                   }
                 }
                """;
        final String publishResponseBody = """
                {
                    "tistory": {
                        "status": "200",
                        "postId": "74",
                        "url": "http://sampleUrl.tistory.com/74"
                    }
                }
                """;

        final String publishPropertyResponseBody = """
                {
                    "tistory":{
                        "status":"200",
                        "item":{
                            "url":"http://oauth.tistory.com",
                            "postUrl":"http://sampleUrl.tistory.com/74",
                            "tags":{
                                "tag":["open", "api"]
                            },
                            "date":"2023-06-01 12:30:00"
                        }
                    }
                }
                """;


        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findByMember(any(Member.class))).willReturn(Optional.of(memberCredentials));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishResponseBody).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishPropertyResponseBody).addHeader("Content-Type", "application/json"));

        // when
        assertThatThrownBy(
                () -> tistoryApiClient.publish(accessToken, content, publishRequest, titleValue)
        ).isInstanceOf(InvalidPublishRequestException.class).hasMessage("발행 정보가 잘못 입력되었습니다.");
    }

    @Test
    @DisplayName("tistory에 발행할 때 발행시간의 형식이 잘못될때 예외")
    void publishTime_fail_formatIsInvalid() {
        // Given
        final String accessToken = "testToken";
        final String content = "<p>Test Content</p>";
        final PublishRequest publishRequest = new PublishRequest(List.of("tag1", "tag2"), "PUBLIC", "", "", "1231254323");

        final String titleValue = "Test Title";
        final String defaultBlogName = """
                {
                   "tistory": {
                     "status": "200",
                     "item": {
                       "id": "blog_oauth_test@daum.net",
                       "userId": "12345",
                       "blogs": [
                         {
                           "name": "oauth-test",
                           "default": "Y",
                           "blogIconUrl": "https://blog_icon_url",
                           "faviconUrl": "https://favicon_url",
                           "profileThumbnailImageUrl": "https://profile_image",
                           "profileImageUrl": "https://profile_image",
                           "role": "소유자",
                           "blogId": "123",
                           "statistics": {
                             "post": "182",
                             "comment": "146",
                             "trackback": "0",
                             "guestbook": "39",
                             "invitation": "0"
                           }
                         }
                       ]
                     }
                   }
                 }
                """;
        final String publishResponseBody = """
                {
                    "tistory": {
                        "status": "200",
                        "postId": "74",
                        "url": "http://sampleUrl.tistory.com/74"
                    }
                }
                """;

        final String publishPropertyResponseBody = """
                {
                    "tistory":{
                        "status":"200",
                        "item":{
                            "url":"http://oauth.tistory.com",
                            "postUrl":"http://sampleUrl.tistory.com/74",
                            "tags":{
                                "tag":["open", "api"]
                            },
                            "date":"2023-06-01 12:30:00"
                        }
                    }
                }
                """;


        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findByMember(any(Member.class))).willReturn(Optional.of(memberCredentials));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishResponseBody).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(defaultBlogName).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(publishPropertyResponseBody).addHeader("Content-Type", "application/json"));

        // When
        // Then
        assertThatThrownBy(
                () -> tistoryApiClient.publish(accessToken, content, publishRequest, titleValue)
        ).isInstanceOf(InvalidPublishRequestException.class).hasMessage("발행 정보가 잘못 입력되었습니다.");
    }

    @Test
    @DisplayName("Tistory 카테고리 조회 테스트")
    void findCategory() {
        // Given
        final Long memberId = 1L;
        final String accessToken = "testToken";
        final List<String> categories = List.of("OAuth2.0 Athentication", "Blog API Series");

        final String categoryListResponse = """
                    {
                      "tistory":{
                        "status":"200",
                        "item":{
                          "url":"oauth",
                          "secondaryUrl":"",
                          "categories":[
                            {
                              "id":"403929",
                              "name":"OAuth2.0 Athentication",
                              "parent":"",
                              "label":"OAuth2.0 Athentication",
                              "entries":"0"
                            },
                            {
                              "id":"403930",
                              "name":"Blog API Series",
                              "parent":"",
                              "label":"Blog API Series",
                              "entries":"0"
                            }
                          ]
                        }
                      }
                    }
                """;

        final Member member = mock(Member.class);
        final MemberCredentials memberCredentials = mock(MemberCredentials.class);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(memberCredentialsRepository.findByMember(any(Member.class))).willReturn(Optional.of(memberCredentials));
        given(memberCredentials.getTistoryToken()).willReturn(Optional.of(accessToken));
        given(memberCredentials.isTistoryConnected()).willReturn(true);
        given(memberCredentials.getTistoryBlogName()).willReturn(Optional.of("jeoninpyo726"));

        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(categoryListResponse).addHeader("Content-Type", "application/json"));

        // When
        final TistoryCategoryListResponse response = tistoryApiClient.findCategory(memberId);

        // Then
        assertThat(response.categories()).anyMatch(category -> categories.contains(category.name()));
    }
}
