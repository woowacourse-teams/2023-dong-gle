package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.CodeBlock;
import org.donggle.backend.domain.writing.block.HorizontalRulesBlock;
import org.donggle.backend.domain.writing.block.ImageBlock;
import org.donggle.backend.domain.writing.block.NormalBlock;

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
        final List<NormalBlock> subBlock = new ArrayList<>();
        String htmlText;

        for (final Block block : blocks) {
            htmlText = createHtmlText(subBlock, block);
            if (!htmlText.isBlank() && !subBlock.isEmpty()) {
                result.append(renderList(subBlock));
                subBlock.clear();
            }
            result.append(htmlText);
        }

        if (!subBlock.isEmpty()) {
            result.append(renderList(subBlock));
        }

        return result.toString();
    }

    private String createHtmlText(final List<NormalBlock> subBlock, final Block block) {
        final BlockType blockType = block.getBlockType();
        String htmlText = "";

        switch (blockType) {
            case HEADING1, HEADING2, HEADING3, HEADING4, HEADING5, HEADING6, PARAGRAPH, BLOCKQUOTE ->
                    htmlText = renderNormalBlock((NormalBlock) block);
            case ORDERED_LIST, UNORDERED_LIST -> subBlock.add((NormalBlock) block);
            case CODE_BLOCK -> htmlText = renderCodeBlock((CodeBlock) block);
            case IMAGE -> htmlText = renderImage((ImageBlock) block);
            case HORIZONTAL_RULES -> htmlText = renderHorizontalRules((HorizontalRulesBlock) block);
        }
        return htmlText;
    }

    private String renderNormalBlock(final NormalBlock block) {
        final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
        final String depth = renderDepth(block.getDepthValue());
        final String rawText = htmlStyleRenderer.render(block.getRawTextValue(), block.getStyles());

        return htmlType.getStartTag() + depth + rawText + htmlType.getEndTag();
    }

    private String renderDepth(final int depth) {
        return HTML_TAB.repeat(Math.max(0, depth));
    }

    private String renderCodeBlock(final CodeBlock block) {
        final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
        final String language = block.getLanguageValue();
        final String rawText = convertToEscape(block.getRawTextValue());

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

    private String renderList(final List<NormalBlock> blocks) {
        final StringBuilder result = new StringBuilder();
        final NormalBlock firstBlock = blocks.get(0);
        final NormalBlock endBlock = blocks.get(blocks.size() - 1);

        addFirstHtmlType(firstBlock, result);
        addInnerHtmlType(blocks, result);
        addEndHtmlType(endBlock, result);

        return result.toString();
    }

    private void addFirstHtmlType(final NormalBlock block, final StringBuilder result) {
        final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
        result.append(htmlType.getStartTag());
    }

    private void addInnerHtmlType(final List<NormalBlock> blocks, final StringBuilder result) {
        final int blockSize = blocks.size();

        for (int i = 0; i < blockSize - 1; i++) {
            final NormalBlock currentBlock = blocks.get(i);
            final NormalBlock nextBlock = blocks.get(i + 1);

            result.append(renderLine(currentBlock, nextBlock));
        }
    }

    private String renderLine(final NormalBlock block, final NormalBlock nextBlock) {
        final String rawText = htmlStyleRenderer.render(block.getRawTextValue(), block.getStyles());
        final String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag();

        if (block.getDepthValue() > nextBlock.getDepthValue()) {
            final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
            return line + htmlType.getEndTag();
        }

        if (block.getDepthValue() == nextBlock.getDepthValue()) {
            if (block.getBlockType().equals(nextBlock.getBlockType())) {
                return line;
            }
            final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
            final HtmlType nextHtmlType = HtmlType.findByBlockType(nextBlock.getBlockType());
            return line + htmlType.getEndTag() + nextHtmlType.getStartTag();
        }

        final HtmlType nextHtmlType = HtmlType.findByBlockType(nextBlock.getBlockType());
        return line + nextHtmlType.getStartTag();
    }

    private void addEndHtmlType(final NormalBlock block, final StringBuilder result) {
        final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
        final String rawText = htmlStyleRenderer.render(block.getRawTextValue(), block.getStyles());
        final String line = HtmlType.LIST.getStartTag() + rawText + HtmlType.LIST.getEndTag() + htmlType.getEndTag();
        result.append(line);
    }

    private String renderImage(final ImageBlock block) {
        final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
        final String caption = block.getImageCaptionValue();
        final String url = block.getImageUrlValue();

        final String startTag = htmlType.getStartTag()
                .replace("caption", caption)
                .replace("url", url);

        return startTag + htmlType.getEndTag();
    }

    private String renderHorizontalRules(final HorizontalRulesBlock block) {
        final HtmlType htmlType = HtmlType.findByBlockType(block.getBlockType());
        return htmlType.getStartTag() + htmlType.getEndTag();
    }
}
