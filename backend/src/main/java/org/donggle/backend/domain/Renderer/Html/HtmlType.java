package org.donggle.backend.domain.Renderer.Html;

import lombok.Getter;
import org.donggle.backend.domain.BlockType;

@Getter
public enum HtmlType {
    HEADING1("<h1>", "</h1>"),
    HEADING2("<h2>", "</h2>"),
    HEADING3("<h3>", "</h3>"),
    HEADING4("<h4>", "</h4>"),
    HEADING5("<h5>", "</h5>"),
    HEADING6("<h6>", "</h6>"),
    BLOCKQUOTE("<blockquote>", "</blockquote>"),
    UNORDERED_LIST("<ul>", "</ul>"),
    ORDERED_LIST("<ol>", "</ol>"),
    LIST("<li>", "</li>"),
    CODE_BLOCK("<pre><code className={`language-${language}`}>", "</code></pre>"),
    PARAGRAPH("<p>", "</p>"),
    IMAGE("<img src=\"url\", alt=\"caption\">", "</img>");

    private final String startTag;
    private final String endTag;

    HtmlType(final String startTag, final String endTag) {
        this.startTag = startTag;
        this.endTag = endTag;
    }

    public static HtmlType findByBlockType(BlockType blockType) {
        return HtmlType.valueOf(blockType.name());
    }
}
