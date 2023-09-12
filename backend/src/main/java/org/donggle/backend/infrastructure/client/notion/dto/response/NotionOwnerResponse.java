package org.donggle.backend.infrastructure.client.notion.dto.response;

public record NotionOwnerResponse(
        String type,
        NotionUserResponse user
) {
}
