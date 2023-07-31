package org.donggle.backend.application.service.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.notion.exception.NotionForbiddenException;
import org.donggle.backend.application.service.notion.exception.NotionInvalidRequestException;
import org.donggle.backend.application.service.notion.exception.NotionNotFoundException;
import org.donggle.backend.application.service.notion.exception.NotionUnAuthorizedException;
import org.donggle.backend.domain.parser.notion.NotionBlockType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


@Component
public class NotionApiService {
    public static final String CHILD_BLOCK_URI = "/blocks/{blockId}/children";
    public static final String BLOCK_URI = "/blocks/{blockId}";
    public static final String AUTHORIZATION = "Authorization";
    public static final String NOTION_VERSION = "Notion-Version";
    public static final String BEARER = "Bearer ";
    public static final String API_VERSION = "2022-06-28";
    public static final int INVALID_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    private static final String NOTION_URL = "https://api.notion.com/v1";

    private final String notionToken;
    private final WebClient webClient;

    public NotionApiService(@Value("${notion_token}") final String notionToken) {
        this.notionToken = notionToken;
        this.webClient = WebClient.create(NOTION_URL);
    }

    public NotionBlockNode retrieveParentBlockNode(final String parentBlockId) {
        return new NotionBlockNode(retrieveBlock(parentBlockId), -1);
    }

    private JsonNode retrieveBlock(final String parentBlockId) {
        return retrieveData(parentBlockId, BLOCK_URI);
    }

    private JsonNode retrieveData(final String blockId, final String childBlockUri) {
        return webClient.get()
                .uri(childBlockUri, blockId)
                .header(AUTHORIZATION, BEARER + this.notionToken)
                .header(NOTION_VERSION, API_VERSION)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handle4xxException(clientResponse.statusCode().value()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException(clientResponse.toString())))
                .bodyToMono(JsonNode.class)
                .block();
    }

    private Mono<? extends Throwable> handle4xxException(final int code) {
        return switch (code) {
            case INVALID_REQUEST -> Mono.error(new NotionInvalidRequestException());
            case UNAUTHORIZED -> Mono.error(new NotionUnAuthorizedException());
            case FORBIDDEN -> Mono.error(new NotionForbiddenException());
            case NOT_FOUND -> Mono.error(new NotionNotFoundException());
            default -> Mono.error(new RuntimeException());
        };
    }

    public List<NotionBlockNode> retrieveBodyBlockNodes(final NotionBlockNode parentNotionBlock) {
        final List<NotionBlockNode> bodyBlockNodes = new ArrayList<>();
        final Deque<NotionBlockNode> notionBlockNodeDeque = new ArrayDeque<>();
        notionBlockNodeDeque.addFirst(parentNotionBlock);

        while (!notionBlockNodeDeque.isEmpty()) {
            final NotionBlockNode notionBlockNode = notionBlockNodeDeque.removeFirst();
            processNotionBlockNode(notionBlockNode, bodyBlockNodes, notionBlockNodeDeque);
        }

        return bodyBlockNodes;
    }

    private void processNotionBlockNode(final NotionBlockNode notionBlockNode, final List<NotionBlockNode> bodyBlockNodes, final Deque<NotionBlockNode> notionBlockNodeDeque) {
        bodyBlockNodes.add(notionBlockNode);

        if (notionBlockNode.hasChildren()) {
            final List<JsonNode> childrenBlocks = retrieveChildrenBlocks(notionBlockNode.getId());
            processChildrenBlocks(childrenBlocks, notionBlockNode, notionBlockNodeDeque);
        }
    }

    private List<JsonNode> retrieveChildrenBlocks(final String blockId) {
        final List<JsonNode> childrenBlocks = new ArrayList<>();
        retrieveData(blockId, CHILD_BLOCK_URI).withArray("results").elements().forEachRemaining(childrenBlocks::add);
        return childrenBlocks;
    }

    private void processChildrenBlocks(final List<JsonNode> childrenBlocks, final NotionBlockNode notionBlockNode, final Deque<NotionBlockNode> notionBlockNodeDeque) {
        final int lastIndex = childrenBlocks.size() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            final JsonNode childBlock = childrenBlocks.get(i);
            if (notionBlockNode.getBlockType() == NotionBlockType.COLUMN || notionBlockNode.getBlockType() == NotionBlockType.COLUMN_LIST) {
                notionBlockNodeDeque.addFirst(new NotionBlockNode(childBlock, 0));
                continue;
            }
            notionBlockNodeDeque.addFirst(new NotionBlockNode(childBlock, notionBlockNode.depth() + 1));
        }
    }
}
