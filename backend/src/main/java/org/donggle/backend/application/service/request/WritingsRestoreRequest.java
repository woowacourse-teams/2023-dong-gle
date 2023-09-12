package org.donggle.backend.application.service.request;

import java.util.List;

public record WritingsRestoreRequest(
        List<Long> writingIds
) {
}
