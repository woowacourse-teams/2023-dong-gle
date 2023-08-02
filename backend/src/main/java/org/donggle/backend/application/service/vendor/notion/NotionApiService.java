package org.donggle.backend.application.service.vendor.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.vendor.exception.VendorApiException;
import org.donggle.backend.application.service.vendor.notion.dto.NotionBlockNode;
import org.donggle.backend.domain.parser.notion.NotionBlockType;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class NotionApiService {
    public static final String CHILD_BLOCK_URI = "/blocks/{blockId}/children";
    public static final String BLOCK_URI = "/blocks/{blockId}";
    public static final String AUTHORIZATION = "Authorization";
    public static final String NOTION_VERSION = "Notion-Version";
    public static final String BEARER = "Bearer ";
    public static final String API_VERSION = "2022-06-28";
    public static final String PLATFORM_NAME = "Notion";
    private static final String NOTION_URL = "https://api.notion.com/v1";

    private final WebClient webClient;

    public NotionApiService() {
        this.webClient = WebClient.create(NOTION_URL);
    }

    public NotionBlockNode retrieveParentBlockNode(final String parentBlockId, final String notionToken) {
        return new NotionBlockNode(retrieveBlock(parentBlockId, notionToken), -1);
    }

    private JsonNode retrieveBlock(final String parentBlockId, final String notionToken) {
        return retrieveData(parentBlockId, BLOCK_URI, notionToken);
    }

    private JsonNode retrieveData(final String blockId, final String childBlockUri, final String notionToken) {
        return webClient.get()
                .uri(childBlockUri, blockId)
                .header(AUTHORIZATION, BEARER + notionToken)
                .header(NOTION_VERSION, API_VERSION)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> VendorApiException.handle4xxException(clientResponse.statusCode().value(), PLATFORM_NAME))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException(clientResponse.toString())))
                .bodyToMono(JsonNode.class)
                .block();
    }

    public List<NotionBlockNode> retrieveBodyBlockNodes(final NotionBlockNode parentNotionBlock, final String notionToken) {
        final List<NotionBlockNode> bodyBlockNodes = new ArrayList<>();
        final Deque<NotionBlockNode> notionBlockNodeDeque = new ArrayDeque<>();
        notionBlockNodeDeque.addFirst(parentNotionBlock);

        while (!notionBlockNodeDeque.isEmpty()) {
            final NotionBlockNode notionBlockNode = notionBlockNodeDeque.removeFirst();
            processNotionBlockNode(notionBlockNode, bodyBlockNodes, notionBlockNodeDeque, notionToken);
        }

        return bodyBlockNodes;
    }

    private void processNotionBlockNode(
            final NotionBlockNode notionBlockNode,
            final List<NotionBlockNode> bodyBlockNodes,
            final Deque<NotionBlockNode> notionBlockNodeDeque,
            final String notionToken
    ) {
        bodyBlockNodes.add(notionBlockNode);

        if (notionBlockNode.hasChildren()) {
            final List<JsonNode> childrenBlocks = retrieveChildrenBlocks(notionBlockNode.getId(), notionToken);
            processChildrenBlocks(childrenBlocks, notionBlockNode, notionBlockNodeDeque);
        }
    }

    private List<JsonNode> retrieveChildrenBlocks(final String blockId, final String notionToken) {
        final List<JsonNode> childrenBlocks = new ArrayList<>();
        retrieveData(blockId, CHILD_BLOCK_URI, notionToken).withArray("results").elements().forEachRemaining(childrenBlocks::add);
        return childrenBlocks;
    }

    private void processChildrenBlocks(
            final List<JsonNode> childrenBlocks,
            final NotionBlockNode notionBlockNode,
            final Deque<NotionBlockNode> notionBlockNodeDeque
    ) {
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
