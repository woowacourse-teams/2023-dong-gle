package org.donggle.backend.ui.common;

public record ErrorContent(String message, String hint, int code){
    public static ErrorContent of(final String message, final String hint, final int code) {
        return new ErrorContent(message, hint, code);
    }
    
    public static ErrorContent of(final String hint, final int code) {
        return new ErrorContent(null, hint, code);
    }
}
