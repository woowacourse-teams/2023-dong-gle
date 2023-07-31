package org.donggle.backend.application.service.notion;

import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.domain.parser.notion.NotionBlockType;

public record NotionBlockNode(JsonNode node, int depth) {
    public boolean hasChildren() {
        return this.node.get("has_children").asBoolean();
    }

    public NotionBlockType getBlockType() {
        return NotionBlockType.findBlockType(getBlockTypeText());
    }

    private String getBlockTypeText() {
        return getTextValue("type");
    }

    private String getTextValue(final String key) {
        return this.node.get(key).asText();
    }

    public String getId() {
        return getTextValue("id");
    }

    public JsonNode getBlockProperties() {
        return this.node.get(getBlockTypeText());
    }
}
