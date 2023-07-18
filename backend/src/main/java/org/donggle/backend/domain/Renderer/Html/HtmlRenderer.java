package org.donggle.backend.domain.Renderer.Html;

import org.donggle.backend.domain.Block;
import org.donggle.backend.domain.BlockType;
import org.donggle.backend.domain.content.CodeBlockContent;
import org.donggle.backend.domain.content.Content;
import org.donggle.backend.domain.content.ImageContent;
import org.donggle.backend.domain.content.NormalContent;

import java.util.ArrayList;
import java.util.List;

public class HtmlRenderer {
    public static final String HTML_TAB = "&emsp;";

    private final HtmlStyleRenderer htmlStyleRenderer;

    public HtmlRenderer(final HtmlStyleRenderer htmlStyleRenderer) {
        this.htmlStyleRenderer = htmlStyleRenderer;
    }

    public String render(final List<Block> blocks) {
        StringBuilder result = new StringBuilder();
        List<NormalContent> subContent = new ArrayList<>();
        String htmlText;

        for (Block block : blocks) {
            htmlText = createHtmlText(subContent, block.getContent());

            if (!htmlText.isBlank() && !subContent.isEmpty()) {
                result.append(renderList(subContent));
                subContent.clear();
            }
            result.append(htmlText);
        }

        return String.valueOf(result);
    }

    private String createHtmlText(final List<NormalContent> subContent, final Content content) {
        BlockType blockType = content.getBlockType();
        String htmlText = "";

        switch (blockType) {
            case HEADING1, HEADING2, HEADING3, HEADING4, HEADING5, HEADING6, PARAGRAPH, BLOCKQUOTE ->
                    htmlText = renderNormalContent((NormalContent) content);
            case ORDERED_LIST, UNORDERED_LIST -> subContent.add((NormalContent) content);
            case CODE_BLOCK -> htmlText = renderCodeBlock((CodeBlockContent) content);
            //TODO: IMAGE
        }
        return htmlText;
    }

    public String renderNormalContent(final NormalContent content) {
        HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        String depth = renderDepth(content.getDepth());
        String rawText = htmlStyleRenderer.render(content.getRawText(), content.getStyles());

        return htmlType.getStartTag() + depth + rawText + htmlType.getEndTag();
    }

    public String renderDepth(final int depth) {
        return HTML_TAB.repeat(Math.max(0, depth));
    }

    public String renderCodeBlock(final CodeBlockContent content) {
        HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        String language = content.getLanguage();
        String rawText = content.getRawText();

        String startTag = htmlType.getStartTag()
                .replace("${language}", language);

        return startTag + rawText + htmlType.getEndTag();
    }

    public String renderList(final List<NormalContent> contents) {
        StringBuilder result = new StringBuilder();
        NormalContent firstContent = contents.get(0);
        NormalContent endContent = contents.get(contents.size() - 1);

        addFirstHtmlType(firstContent, result);
        addInnerHtmlType(contents, result);
        addEndHtmlType(endContent, result);

        return String.valueOf(result);
    }

    private void addFirstHtmlType(final NormalContent content, final StringBuilder result) {
        HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        result.append(htmlType.getStartTag());
    }

    private void addInnerHtmlType(final List<NormalContent> contents, final StringBuilder result) {
        int contentSize = contents.size();

        for (int i = 0; i < contentSize - 1; i++) {
            NormalContent currentContent = contents.get(i);
            NormalContent nextContent = contents.get(i + 1);

            result.append(renderLine(currentContent, nextContent));
        }
    }

    private String renderLine(final NormalContent content, NormalContent nextContent) {
        String rawText = htmlStyleRenderer.render(content.getRawText(), content.getStyles());
        String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag();

        if (content.getDepth() > nextContent.getDepth()) {
            HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
            return line + htmlType.getEndTag();
        }

        if (content.getDepth() == nextContent.getDepth()) {
            if (content.getBlockType().equals(nextContent.getBlockType())) {
                return line;
            }
            HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
            HtmlType nextHtmlType = HtmlType.findByBlockType(nextContent.getBlockType());
            return line + htmlType.getEndTag() + nextHtmlType.getStartTag();
        }

        HtmlType nextHtmlType = HtmlType.findByBlockType(nextContent.getBlockType());
        return line + nextHtmlType.getStartTag();
    }

    private void addEndHtmlType(final NormalContent content, final StringBuilder result) {
        HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        String line = HtmlType.LIST.getStartTag() + content.getRawText() + HtmlType.LIST.getEndTag() + htmlType.getEndTag();
        result.append(line);
    }

    public String renderImage(final ImageContent content) {
        HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        String caption = content.getCaption();
        String url = content.getUrl();

        String startTag = htmlType.getStartTag()
                .replace("caption", caption)
                .replace("url", url);

        return startTag + htmlType.getEndTag();
    }
}
