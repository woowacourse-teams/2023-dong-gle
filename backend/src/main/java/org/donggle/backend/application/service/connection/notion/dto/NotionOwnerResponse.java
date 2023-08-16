package org.donggle.backend.application.service.connection.notion.dto;

public record NotionOwnerResponse(
        String type,
        NotionUserResponse user
) {
}
