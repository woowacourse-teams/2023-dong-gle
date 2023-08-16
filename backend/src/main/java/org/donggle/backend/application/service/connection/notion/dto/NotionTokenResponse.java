package org.donggle.backend.application.service.connection.notion.dto;

public record NotionTokenResponse(
        String access_token,            // accessToken
        String bot_id,                  // 승인에 대한 식별자
        String duplicated_template_id,
        // 사용자 작업 공간에 생성된 새 페이지(개발자가 통합과 함께 제공한 템플릿의 복제본)의 ID, 개발자가 통합을 위한 템플릿을 제공하지 않은 경우 값은 null
        NotionOwnerResponse owner,      // 이 통합을 보고 공유할 수 있는 사람에 대한 정보가 포함된 개체
        String workspace_icon,          // UI에서 이 승인을 표시하는 데 사용할 수 있는 이미지의 URL
        String workspace_id,            // 이 승인이 발생한 워크스페이스의 ID
        String workspace_name           // UI에서 이 승인을 표시하는 데 사용할 수 있는 사람이 읽을 수 있는 이름
) {
}
