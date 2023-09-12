package org.donggle.backend.infrastructure.client.notion.dto.response;

public record NotionTokenResponse(
        String access_token,
        String bot_id,
        String duplicated_template_id,
        NotionOwnerResponse owner,
        String workspace_icon,
        String workspace_id,
        String workspace_name
) {
}
