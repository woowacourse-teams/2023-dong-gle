package org.donggle.backend.domain.renderer.html;

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
        final StringBuilder result = new StringBuilder();
        final List<NormalContent> subContent = new ArrayList<>();
        String htmlText;

        for (Block block : blocks) {
            htmlText = createHtmlText(subContent, block.getContent());
            if (!htmlText.isBlank() && !subContent.isEmpty()) {
                result.append(renderList(subContent));
                subContent.clear();
            }

            result.append(htmlText);
        }

        if (!subContent.isEmpty()) {
            result.append(renderList(subContent));
        }

        return result.toString();
    }

    private String createHtmlText(final List<NormalContent> subContent, final Content content) {
        final BlockType blockType = content.getBlockType();
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

    private String renderNormalContent(final NormalContent content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String depth = renderDepth(content.getDepth());
        final String rawText = htmlStyleRenderer.render(content.getRawText(), content.getStyles());

        return htmlType.getStartTag() + depth + rawText + htmlType.getEndTag();
    }

    private String renderDepth(final int depth) {
        return HTML_TAB.repeat(Math.max(0, depth));
    }

    private String renderCodeBlock(final CodeBlockContent content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String language = content.getLanguage();
        final String rawText = content.getRawText();

        final String startTag = htmlType.getStartTag()
                .replace("${language}", language);

        return startTag + rawText + htmlType.getEndTag();
    }

    private String renderList(final List<NormalContent> contents) {
        final StringBuilder result = new StringBuilder();
        final NormalContent firstContent = contents.get(0);
        final NormalContent endContent = contents.get(contents.size() - 1);

        addFirstHtmlType(firstContent, result);
        addInnerHtmlType(contents, result);
        addEndHtmlType(endContent, result);

        return result.toString();
    }

    private void addFirstHtmlType(final NormalContent content, final StringBuilder result) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        result.append(htmlType.getStartTag());
    }

    private void addInnerHtmlType(final List<NormalContent> contents, final StringBuilder result) {
        final int contentSize = contents.size();

        for (int i = 0; i < contentSize - 1; i++) {
            final NormalContent currentContent = contents.get(i);
            final NormalContent nextContent = contents.get(i + 1);

            result.append(renderLine(currentContent, nextContent));
        }
    }

    private String renderLine(final NormalContent content, NormalContent nextContent) {
        final String rawText = htmlStyleRenderer.render(content.getRawText(), content.getStyles());
        final String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag();

        if (content.getDepth() > nextContent.getDepth()) {
            final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
            return line + htmlType.getEndTag();
        }

        if (content.getDepth() == nextContent.getDepth()) {
            if (content.getBlockType().equals(nextContent.getBlockType())) {
                return line;
            }
            final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
            final HtmlType nextHtmlType = HtmlType.findByBlockType(nextContent.getBlockType());
            return line + htmlType.getEndTag() + nextHtmlType.getStartTag();
        }

        final HtmlType nextHtmlType = HtmlType.findByBlockType(nextContent.getBlockType());
        return line + nextHtmlType.getStartTag();
    }

    private void addEndHtmlType(final NormalContent content, final StringBuilder result) {
        HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        String rawText = htmlStyleRenderer.render(content.getRawText(), content.getStyles());
        String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag() + htmlType.getEndTag();
        result.append(line);
    }

    private String renderImage(final ImageContent content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String caption = content.getCaption();
        final String url = content.getUrl();

        final String startTag = htmlType.getStartTag()
                .replace("caption", caption)
                .replace("url", url);

        return startTag + htmlType.getEndTag();
    }
}
