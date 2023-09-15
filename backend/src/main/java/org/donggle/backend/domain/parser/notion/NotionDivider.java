package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;

public record NotionDivider() {
    public static NotionDivider from(final NotionBlockNodeResponse blockNode) {
        return new NotionDivider();
    }

    public String parseRawText() {
        return "---";
    }
}
