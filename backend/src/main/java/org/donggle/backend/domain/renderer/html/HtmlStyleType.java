package org.donggle.backend.domain.renderer.html;

import lombok.Getter;
import org.donggle.backend.domain.writing.StyleType;

@Getter
public enum HtmlStyleType {
    BOLD("<strong>", "</strong>"),
    ITALIC("<em>", "</em>"),
    CODE("<code>", "</code>"),
    LINK("<a href=\"", "\">"),
    CAPTION("", "</a>"),
    EMPTY_CAPTION_LINK("<a href=\"", "\"></a>"),
    STRIKETHROUGH("<s>", "</s>");

    private final String startTag;
    private final String endTag;

    HtmlStyleType(final String startTag, final String endTag) {
        this.startTag = startTag;
        this.endTag = endTag;
    }

    public static HtmlStyleType findByStyleType(final StyleType styleType) {
        return HtmlStyleType.valueOf(styleType.name());
    }
}
