package org.donggle.backend.ui.response;

public record TistoryConnectionResponse(
        boolean isConnected,
        String blogName
) {
}
