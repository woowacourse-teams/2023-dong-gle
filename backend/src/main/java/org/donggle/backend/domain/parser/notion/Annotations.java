package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;

public record Annotations(
        boolean bold,
        boolean italic,
        boolean strikethrough,
        boolean underline,
        boolean code,
        String color
) {
    public static Annotations from(final JsonNode annotationsNode) {
        final boolean bold = annotationsNode.get("bold").asBoolean();
        final boolean italic = annotationsNode.get("italic").asBoolean();
        final boolean strikethrough = annotationsNode.get("strikethrough").asBoolean();
        final boolean underline = annotationsNode.get("underline").asBoolean();
        final boolean code = annotationsNode.get("code").asBoolean();
        final String color = annotationsNode.get("color").asText();
        return new Annotations(bold, italic, strikethrough, underline, code, color);
    }

    public static Annotations empty() {
        return new Annotations(false, false, false, false, false, "default");
    }
}
