package org.donggle.backend.domain.renderer.html;

import lombok.Getter;
import org.donggle.backend.domain.writing.BlockType;

@Getter
public enum HtmlType {
    HEADING1("<h1>", "</h1>"),
    HEADING2("<h2>", "</h2>"),
    HEADING3("<h3>", "</h3>"),
    HEADING4("<h4>", "</h4>"),
    HEADING5("<h5>", "</h5>"),
    HEADING6("<h6>", "</h6>"),
    BLOCKQUOTE("<blockquote>", "</blockquote>"),
    CHECKED_TASK_LIST("<div><input type=\"checkbox\" checked>", "</input></div>"),
    UNCHECKED_TASK_LIST("<div><input type=\"checkbox\" unchecked>", "</input></div>"),
    UNORDERED_LIST("<ul style=\"list-style-type: disc;\" data-ke-list-type=\"disc\">", "</ul>"),
    ORDERED_LIST("<ol style=\"list-style-type: decimal;\" data-ke-list-type=\"decimal\">", "</ol>"),
    LIST("<li>", "</li>"),
    CODE_BLOCK("<pre><code class=\"language-${language}\">", "</code></pre>"),
    IMAGE("<img src=\"url\", alt=\"caption\">", "</img>"),
    HORIZONTAL_RULES("<hr>", "</hr>"),
    TOGGLE("<details><summary>", "</summary>"),
    PARAGRAPH("<p>", "</p>");

    private final String startTag;
    private final String endTag;

    HtmlType(final String startTag, final String endTag) {
        this.startTag = startTag;
        this.endTag = endTag;
    }

    public static HtmlType findByBlockType(final BlockType blockType) {
        return HtmlType.valueOf(blockType.name());
    }
}
