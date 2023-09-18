package org.donggle.backend.application.service.request;

import org.donggle.backend.domain.blog.PublishStatus;

import java.util.List;
import java.util.Objects;

public record PublishRequest(
        List<String> tags,
        String publishStatus,
        String password,
        Long categoryId,
        String publishTime
) {
    public static PublishRequest tistory(final PublishRequest publishRequest) {
        final PublishStatus status = PublishStatus.from(publishRequest.publishStatus);
        if (status == PublishStatus.PUBLIC && !publishRequest.password().isBlank()) {
            throw new IllegalArgumentException("공개글은 비밀번호를 입력할 수 없습니다.");
        }
        if (status == PublishStatus.PRIVATE) {
            if (!publishRequest.publishTime.isBlank()) {
                throw new IllegalArgumentException("비밀글은 예약 시간을 정할 수 없습니다.");
            }
            if (!publishRequest.password().isBlank()) {
                throw new IllegalArgumentException("비밀글은 비밀번호를 입력할 수 없습니다.");
            }
        }
        if (status == PublishStatus.PROTECT && publishRequest.password().isBlank()) {
            throw new IllegalArgumentException("보호글은 비밀번호가 입력되어야 합니다.");
        }
        return publishRequest;
    }

    public static PublishRequest medium(final PublishRequest publishRequest) {
        if (Objects.nonNull(publishRequest.password())) {
            throw new IllegalArgumentException("미디움은 비밀번호를 입력할 수 없습니다.");
        }
        if (Objects.nonNull(publishRequest.categoryId())) {
            throw new IllegalArgumentException("미디움은 카테고리를 선택할 수 없습니다.");
        }
        if (Objects.nonNull(publishRequest.publishTime())) {
            throw new IllegalArgumentException("미디움은 예약 시간을 선택할 수 없습니다.");
        }
        return publishRequest;
    }
}
