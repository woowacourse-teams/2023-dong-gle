package org.donggle.backend.infrastructure.client.tistory;

import org.donggle.backend.application.client.BlogClient;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.blog.PublishStatus;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.exception.business.InvalidPublishRequestException;
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
import org.donggle.backend.ui.response.TistoryCategoryListResposnse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
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

    @Autowired
    public TistoryApiClient(final MemberRepository memberRepository,
                            final MemberCredentialsRepository memberCredentialsRepository) {
        this.memberRepository = memberRepository;
        this.webClient = WebClient.create(TISTORY_URL);
        this.memberCredentialsRepository = memberCredentialsRepository;
    }

    public TistoryApiClient(final MemberRepository memberRepository,
                            final MemberCredentialsRepository memberCredentialsRepository,
                            final WebClient webClient) {
        this.memberRepository = memberRepository;
        this.memberCredentialsRepository = memberCredentialsRepository;
        this.webClient = webClient;
    }

    @Override
    public PublishResponse publish(final String accessToken, final String content, final PublishRequest publishRequest, final String titleValue) {
        final TistoryPublishRequest request = makePublishRequest(accessToken, titleValue, content, publishRequest);
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
                .blogName(findDefaultBlogName(accessToken))
                .build();
    }

    private TistoryPublishRequest makePublishRequest(
            final String accessToken,
            final String titleValue,
            final String content,
            final PublishRequest publishRequest
    ) {
        final PublishStatus publishStatus = PublishStatus.from(publishRequest.publishStatus());
        if (publishStatus == PublishStatus.PROTECT) {
            return TistoryPublishRequest.builder()
                    .access_token(accessToken)
                    .blogName(findDefaultBlogName(accessToken))
                    .output("json")
                    .title(titleValue)
                    .content(content)
                    .visibility(publishStatus.getTistory())
                    .category(publishRequest.categoryId())
                    .tag(String.join(",", publishRequest.tags()))
                    .published(makePublishTime(publishRequest.publishTime()))
                    .password(publishRequest.password())
                    .build();
        }
        return TistoryPublishRequest.builder()
                .access_token(accessToken)
                .blogName(findDefaultBlogName(accessToken))
                .output("json")
                .title(titleValue)
                .content(content)
                .visibility(publishStatus.getTistory())
                .category(publishRequest.categoryId())
                .tag(String.join(",", publishRequest.tags()))
                .published(makePublishTime(publishRequest.publishTime()))
                .build();
    }

    public TistoryCategoryListResposnse findCategory(final Long memberId) {
        final MemberCredentials memberCredentials = getMemberCredentials(memberId);
        final String categoryListUri = UriComponentsBuilder.fromUriString("/category/list")
                .queryParam("access_token", memberCredentials.getTistoryToken())
                .queryParam("output", "json")
                .queryParam("blogName", memberCredentials.getTistoryBlogName())
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
        return new TistoryCategoryListResposnse(
                categoryList.tistory().item().categories().stream()
                        .map(category -> new TistoryCategoryListResposnse.TistoryCategoryResponse(category.id(), category.name()))
                        .toList());
    }

    private MemberCredentials getMemberCredentials(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        final MemberCredentials memberCredentials = memberCredentialsRepository.findByMember(member)
                .orElseThrow(NoSuchElementException::new);
        if (!memberCredentials.isTistoryConnected()) {
            throw new TistoryNotConnectedException();
        }
        return memberCredentials;
    }

    private Long makePublishTime(final String publishTime) {
        if (publishTime.isBlank()) {
            return Instant.now().getEpochSecond();
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            final Date date = formatter.parse(publishTime);
            final Instant instant = date.toInstant();
            if (instant.isBefore(Instant.now())) {
                throw new InvalidPublishRequestException("현재 시간보다 과거의 시간은 입력될 수 없습니다.");
            }
            return instant.getEpochSecond();
        } catch (final ParseException e) {
            throw new InvalidPublishRequestException("예약 시간 입력 형식이 잘못되었습니다.");
        }
    }

    public String findDefaultBlogName(final String access_token) {
        final String blogInfoUri = UriComponentsBuilder.fromUriString("/blog/info")
                .queryParam("access_token", access_token)
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

    private TistoryGetWritingResponseWrapper findPublishProperty(final TistoryPublishPropertyRequest request) {
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
