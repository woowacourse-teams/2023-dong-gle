package org.donggle.backend.application.service.connection.notion.dto;

public record NotionTokenRequest(String grant_type, String code, String redirect_uri) {
}
