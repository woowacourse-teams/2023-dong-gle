package org.donggle.backend.domain;

public enum OrderStatus {
    END(-1L),
    DISCONNECTION(-2L);

    private final Long statusValue;

    OrderStatus(final Long statusValue) {
        this.statusValue = statusValue;
    }

    public Long getStatusValue() {
        return statusValue;
    }
}
