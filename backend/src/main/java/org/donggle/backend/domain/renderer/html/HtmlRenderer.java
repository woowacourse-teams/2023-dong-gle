package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.CodeBlock;
import org.donggle.backend.domain.writing.block.HorizontalRulesBlock;
import org.donggle.backend.domain.writing.block.ImageBlock;
import org.donggle.backend.domain.writing.block.NormalBlock;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
        boolean isToggle = false;
        int toggleDepth = 0;

        for (final Block block : blocks) {
            if (block.getBlockType() == BlockType.TOGGLE) {
                isToggle = true;
                toggleDepth = block.getDepthValue();
            }

            htmlText = createHtmlText(subBlock, block);
            if (!htmlText.isBlank() && !subBlock.isEmpty()) {
                result.append(renderList(subBlock));
                subBlock.clear();
            }
            result.append(htmlText);

            if ((block.getBlockType() != BlockType.TOGGLE) && isToggle && (toggleDepth >= block.getDepthValue())) {
                isToggle = false;
                toggleDepth = 0;
                if (!subBlock.isEmpty()) {
                    result.append(renderList(subBlock));
                    subBlock.clear();
                }
                result.append("</details>");
            }
        }

        if (!subBlock.isEmpty()) {
            result.append(renderList(subBlock));
        }
        if (isToggle) {
            result.append("</details>");
        }

        return result.toString();
    }

    private String createHtmlText(final List<NormalBlock> subBlock, final Block block) {
        final BlockType blockType = block.getBlockType();
        String htmlText = "";
        switch (blockType) {
            case ORDERED_LIST, UNORDERED_LIST -> subBlock.add((NormalBlock) block);
            case CODE_BLOCK -> htmlText = renderCodeBlock((CodeBlock) block);
            case IMAGE -> htmlText = renderImage((ImageBlock) block);
            case HORIZONTAL_RULES -> htmlText = renderHorizontalRules((HorizontalRulesBlock) block);
            default -> htmlText = renderNormalBlock((NormalBlock) block);
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
        final String startTag = switch (language) {
            case "plain text" -> htmlType.getStartTag().replace("${language}", "plaintext");
            default -> htmlType.getStartTag().replace("${language}", language);
        };


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
                .replaceAll("    ", "&Tab;")
                .replaceAll("\\t", "&Tab;");
    }

    private void convertToEscapeLetter(final char letter, final StringBuffer bufferText) {
        switch (letter) {
            case '<' -> bufferText.append("&lt;");
            case '>' -> bufferText.append("&gt;");
            case '&' -> bufferText.append("&amp;");
            case '"' -> bufferText.append("&quot;");
            default -> bufferText.append(letter);
        }
    }

    private String renderList(final List<NormalBlock> blocks) {
        final StringBuilder result = new StringBuilder();
        final Deque<String> ends = new ArrayDeque<>();

        NormalBlock preBlock = blocks.get(0);
        final HtmlType preHtmlType = HtmlType.findByBlockType(preBlock.getBlockType());
        result.append(preHtmlType.getStartTag());
        result.append(HtmlType.LIST.getStartTag())
                .append(htmlStyleRenderer.render(preBlock.getRawTextValue(), preBlock.getStyles()))
                .append(HtmlType.LIST.getEndTag());

        ends.push(preHtmlType.getEndTag());
        int currentDepth = preBlock.getDepthValue();

        final int blockSize = blocks.size();
        for (int i = 1; i < blockSize; i++) {
            final NormalBlock currentBlock = blocks.get(i);
            final HtmlType currentHtmlType = HtmlType.findByBlockType(currentBlock.getBlockType());
            final int depthDifference = currentDepth - currentBlock.getDepthValue();

            if (depthDifference < 0) {
                result.append(currentHtmlType.getStartTag());
                currentDepth += 1;
                ends.addLast(currentHtmlType.getEndTag());
            }
            if (depthDifference > 0) {
                for (int j = 0; j < depthDifference; j++) {
                    result.append(ends.removeLast());
                    currentDepth = currentDepth - depthDifference;
                }
                if ((preBlock.getBlockType() != currentBlock.getBlockType()) && !ends.isEmpty()) {
                    result.append(ends.removeLast());
                    result.append(currentHtmlType.getStartTag());
                }
                ends.addLast(currentHtmlType.getEndTag());
            }
            if (depthDifference == 0) {
                if (preBlock.getBlockType() != currentBlock.getBlockType()) {
                    result.append(ends.removeLast());
                    result.append(currentHtmlType.getStartTag());
                    ends.addLast(currentHtmlType.getEndTag());
                }
            }
            result.append(HtmlType.LIST.getStartTag())
                    .append(htmlStyleRenderer.render(currentBlock.getRawTextValue(), currentBlock.getStyles()))
                    .append(HtmlType.LIST.getEndTag());
            preBlock = currentBlock;
        }

        while (!ends.isEmpty()) {
            result.append(ends.removeLast());
        }

        return result.toString();
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
