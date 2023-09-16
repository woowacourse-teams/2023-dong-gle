package org.donggle.backend.infrastructure.client.notion.dto.request;

public record NotionTokenRequest(String grant_type, String code, String redirect_uri) {
}
