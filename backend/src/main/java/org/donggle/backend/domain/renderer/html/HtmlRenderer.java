package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.Block;
import org.donggle.backend.domain.writing.content.CodeBlock;
import org.donggle.backend.domain.writing.content.ImageBlock;
import org.donggle.backend.domain.writing.content.NormalBlock;

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
        final List<NormalBlock> subContent = new ArrayList<>();
        String htmlText;

        for (final Block block : blocks) {
            htmlText = createHtmlText(subContent, block);
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

    private String createHtmlText(final List<NormalBlock> subContent, final Block block) {
        final BlockType blockType = block.getBlockType();
        String htmlText = "";

        switch (blockType) {
            case HEADING1, HEADING2, HEADING3, HEADING4, HEADING5, HEADING6, PARAGRAPH, BLOCKQUOTE ->
                    htmlText = renderNormalContent((NormalBlock) block);
            case ORDERED_LIST, UNORDERED_LIST -> subContent.add((NormalBlock) block);
            case CODE_BLOCK -> htmlText = renderCodeBlock((CodeBlock) block);
            case IMAGE -> htmlText = renderImage((ImageBlock) block);
        }
        return htmlText;
    }

    private String renderNormalContent(final NormalBlock content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String depth = renderDepth(content.getDepthValue());
        final String rawText = htmlStyleRenderer.render(content.getRawTextValue(), content.getStyles());

        return htmlType.getStartTag() + depth + rawText + htmlType.getEndTag();
    }

    private String renderDepth(final int depth) {
        return HTML_TAB.repeat(Math.max(0, depth));
    }

    private String renderCodeBlock(final CodeBlock content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String language = content.getLanguageValue();
        final String rawText = convertToEscape(content.getRawTextValue());

        final String startTag = htmlType.getStartTag()
                .replace("${language}", language);

        return startTag + rawText + htmlType.getEndTag();
    }

    private String convertToEscape(final String rawText) {
        final StringBuffer bufferText = new StringBuffer();
        final int len = rawText.length();
        for (int i = 0; i < len; i++) {
            final char letter = rawText.charAt(i);
            convertToEscapeLetter(letter, bufferText);
        }
        return bufferText.toString()
                .replaceAll("\\n", "&NewLine;")
                .replaceAll("    ", "&Tab;");
    }

    private void convertToEscapeLetter(final char letter, final StringBuffer bufferText) {
        switch (letter) {
            case '<':
                bufferText.append("&lt;");
                break;
            case '>':
                bufferText.append("&gt;");
                break;
            case '&':
                bufferText.append("&amp;");
                break;
            case '"':
                bufferText.append("&quot;");
                break;
            default:
                bufferText.append(letter);
        }
    }

    private String renderList(final List<NormalBlock> contents) {
        final StringBuilder result = new StringBuilder();
        final NormalBlock firstContent = contents.get(0);
        final NormalBlock endContent = contents.get(contents.size() - 1);

        addFirstHtmlType(firstContent, result);
        addInnerHtmlType(contents, result);
        addEndHtmlType(endContent, result);

        return result.toString();
    }

    private void addFirstHtmlType(final NormalBlock content, final StringBuilder result) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        result.append(htmlType.getStartTag());
    }

    private void addInnerHtmlType(final List<NormalBlock> contents, final StringBuilder result) {
        final int contentSize = contents.size();

        for (int i = 0; i < contentSize - 1; i++) {
            final NormalBlock currentContent = contents.get(i);
            final NormalBlock nextContent = contents.get(i + 1);

            result.append(renderLine(currentContent, nextContent));
        }
    }

    private String renderLine(final NormalBlock content, final NormalBlock nextContent) {
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

    private void addEndHtmlType(final NormalBlock content, final StringBuilder result) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String rawText = htmlStyleRenderer.render(content.getRawTextValue(), content.getStyles());
        final String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag() + htmlType.getEndTag();
        result.append(line);
    }

    private String renderImage(final ImageBlock content) {
        final HtmlType htmlType = HtmlType.findByBlockType(content.getBlockType());
        final String caption = content.getImageCaptionValue();
        final String url = content.getImageUrlValue();

        final String startTag = htmlType.getStartTag()
                .replace("caption", caption)
                .replace("url", url);

        return startTag + htmlType.getEndTag();
    }
}
