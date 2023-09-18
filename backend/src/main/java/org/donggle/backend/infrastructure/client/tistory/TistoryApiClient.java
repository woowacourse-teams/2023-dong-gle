package org.donggle.backend.infrastructure.client.tistory;

import org.donggle.backend.application.client.BlogClient;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.business.TistoryNotConnectedException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.infrastructure.client.exception.ClientInternalServerError;
import org.donggle.backend.infrastructure.client.tistory.dto.request.TistoryPublishPropertyRequest;
import org.donggle.backend.infrastructure.client.tistory.dto.request.TistoryPublishRequest;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryBlogNameResponse;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryCategoryListResponseWrapper;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryGetWritingResponseWrapper;
import org.donggle.backend.infrastructure.client.tistory.dto.response.TistoryPublishWritingResponseWrapper;
import org.donggle.backend.ui.response.PublishResponse;
import org.donggle.backend.ui.response.TistoryCategoryListResposne;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

import static org.donggle.backend.domain.blog.BlogType.TISTORY;
import static org.donggle.backend.infrastructure.client.exception.ClientException.handle4xxException;

@Component
public class TistoryApiClient implements BlogClient {
    private static final String PLATFORM_NAME = "Tistory";
    private static final String TISTORY_URL = "https://www.tistory.com/apis";

    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    public TistoryApiClient(final MemberRepository memberRepository,
                            final MemberCredentialsRepository memberCredentialsRepository) {
        this.memberRepository = memberRepository;
        this.webClient = WebClient.create(TISTORY_URL);
        this.memberCredentialsRepository = memberCredentialsRepository;
    }

    @Override
    public PublishResponse publish(final String accessToken, final String content, final List<String> tags, final String titleValue) {
        final TistoryPublishRequest request = makePublishRequest(accessToken, titleValue, content, tags);
        final TistoryPublishWritingResponseWrapper response = webClient.post()
                .uri("/post/write?")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryPublishWritingResponseWrapper.class)
                .block();
        return findPublishProperty(makeTistoryPublishPropertyRequest(accessToken, response.tistory().postId()))
                .toPublishResponse();
    }

    private TistoryPublishPropertyRequest makeTistoryPublishPropertyRequest(final String accessToken, final Long postId) {
        return TistoryPublishPropertyRequest.builder()
                .access_token(accessToken)
                .postId(postId)
                .blogName(getDefaultTistoryBlogName(accessToken))
                .build();
    }

    public TistoryPublishRequest makePublishRequest(
            final String accessToken,
            final String titleValue,
            final String content,
            final List<String> tags
    ) {
        return TistoryPublishRequest.builder()
                .access_token(accessToken)
                .blogName(getDefaultTistoryBlogName(accessToken))
                .output("json")
                .title(titleValue)
                .content(content)
                .tag(String.join(",", tags))
                .build();
    }

    public TistoryCategoryListResposne findCategory(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(member)
                .orElseThrow(NoSuchElementException::new);
        final String accessToken = memberCredentials.getBlogToken(TISTORY)
                .orElseThrow(TistoryNotConnectedException::new);
        final String blogName = memberCredentials.getTistoryBlogName()
                .orElseThrow(TistoryNotConnectedException::new);

        final String categoryListUri = UriComponentsBuilder.fromUriString("/category/list")
                .queryParam("access_token", accessToken)
                .queryParam("output", "json")
                .queryParam("blogName", blogName)
                .build()
                .toUriString();

        final TistoryCategoryListResponseWrapper categoryList = webClient.get()
                .uri(categoryListUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryCategoryListResponseWrapper.class)
                .block();

        return new TistoryCategoryListResposne(
                categoryList.tistory().item().categories().stream()
                        .map(category -> new TistoryCategoryListResposne.TistoryCategoryResponse(category.id(), category.name()))
                        .toList());
    }

    public String getDefaultTistoryBlogName(final String accessToken) {
        final String blogInfoUri = UriComponentsBuilder.fromUriString(TISTORY_URL)
                .path("/blog/info")
                .queryParam("access_token", accessToken)
                .queryParam("output", "json")
                .build()
                .toUriString();
        final TistoryBlogNameResponse blogInfo = webClient.get()
                .uri(blogInfoUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryBlogNameResponse.class)
                .block();
        return blogInfo.tistory().item().blogs().stream()
                .filter(blog -> blog.defaultValue().equals("Y"))
                .map(TistoryBlogNameResponse.TistoryBlogInfoResponse.TistoryBlogResponse::name)
                .findFirst()
                .orElseThrow();
    }

    public TistoryGetWritingResponseWrapper findPublishProperty(final TistoryPublishPropertyRequest request) {
        final String publishPropertyUri = UriComponentsBuilder.fromUriString("/post/read")
                .queryParam("access_token", request.access_token())
                .queryParam("blogName", request.blogName())
                .queryParam("postId", request.postId())
                .queryParam("output", "json")
                .build()
                .toUriString();
        return webClient.get()
                .uri(publishPropertyUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(e -> new ClientInternalServerError(PLATFORM_NAME)))
                .bodyToMono(TistoryGetWritingResponseWrapper.class)
                .block();
    }

    @Override
    public BlogType getBlogType() {
        return TISTORY;
    }
}
