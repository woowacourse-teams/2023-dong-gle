package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.Block;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.CodeBlockContent;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.domain.writing.content.ImageContent;
import org.donggle.backend.domain.writing.content.NormalContent;

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

        for (final Block block : blocks) {
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
            case IMAGE -> htmlText = renderImage((ImageContent) content);
        }
        return htmlText;
    }

    private String renderNormalContent(final NormalContent content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String depth = renderDepth(content.getDepthValue());
        final String rawText = htmlStyleRenderer.render(content.getRawTextValue(), content.getStyles());

        return htmlType.getStartTag() + depth + rawText + htmlType.getEndTag();
    }

    private String renderDepth(final int depth) {
        return HTML_TAB.repeat(Math.max(0, depth));
    }

    private String renderCodeBlock(final CodeBlockContent content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String language = content.getLanguageValue();
        final String rawText = content.getRawTextValue();

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

    private String renderLine(final NormalContent content, final NormalContent nextContent) {
        final String rawText = htmlStyleRenderer.render(content.getRawTextValue(), content.getStyles());
        final String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag();

        if (content.getDepthValue() > nextContent.getDepthValue()) {
            final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
            return line + htmlType.getEndTag();
        }

        if (content.getDepthValue() == nextContent.getDepthValue()) {
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
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String rawText = htmlStyleRenderer.render(content.getRawTextValue(), content.getStyles());
        final String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag() + htmlType.getEndTag();
        result.append(line);
    }

    private String renderImage(final ImageContent content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String caption = content.getImageCaptionValue();
        final String url = content.getImageUrlValue();

        final String startTag = htmlType.getStartTag()
                .replace("caption", caption)
                .replace("url", url);

        return startTag + htmlType.getEndTag();
    }
}
