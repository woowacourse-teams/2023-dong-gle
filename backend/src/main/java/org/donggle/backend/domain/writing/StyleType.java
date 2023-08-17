package org.donggle.backend.domain.writing;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public enum StyleType {
    BOLD("(\\*\\*|\\_\\_)(.*?)(\\*\\*|\\_\\_)"),
    ITALIC("(\\*|\\_)(.*?)(\\*|\\_)"),
    CODE("(\\`)(.*?)(\\`)"),
    LINK("(\\[)(.*)(\\]\\()(.*)(\\))"),
    STRIKETHROUGH("(\\~\\~)(.*?)(\\~\\~)");

    private final Pattern pattern;

    StyleType(final String content) {
        this.pattern = Pattern.compile(content);
    }
}
