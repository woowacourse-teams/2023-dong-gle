package org.donggle.backend.application.service.oauth.notion.dto;

public record NotionOwnerResponse(
        String type,
        NotionUserResponse user
) {
}
