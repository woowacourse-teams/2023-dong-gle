package org.donggle.backend.application.service.request;

import java.util.List;

public record PublishRequest(String publishTo, List<String> tags) {
}
