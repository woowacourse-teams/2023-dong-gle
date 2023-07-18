package org.donggle.backend.domain.Renderer.Html;

import lombok.Getter;
import org.donggle.backend.domain.StyleType;

@Getter
public enum HtmlStyleType {
    BOLD("<strong>", "</strong>"),
    ITALIC("<em>", "</em>"),
    CODE("<code>", "</code>");

    private final String startTag;
    private final String endTag;

    HtmlStyleType(final String startTag, final String endTag) {
        this.startTag = startTag;
        this.endTag = endTag;
    }

    public static HtmlStyleType findByStyleType(StyleType styleType) {
        return HtmlStyleType.valueOf(styleType.name());
    }
}