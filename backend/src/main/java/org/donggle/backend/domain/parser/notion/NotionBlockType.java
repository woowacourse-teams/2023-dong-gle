package org.donggle.backend.domain.parser.notion;

import java.util.Arrays;

public enum NotionBlockType {
    BOOKMARK,
    BULLETED_LIST_ITEM,
    BREADCRUMB,
    CALLOUT,
    CHILD_DATABASE,
    CHILD_PAGE,
    CODE,
    COLUMN,
    COLUMN_LIST,
    DIVIDER,
    EMBED,
    FILE,
    HEADING_1,
    HEADING_2,
    HEADING_3,
    IMAGE,
    LINK_PREVIEW,
    LINK_TO_PAGE,
    NUMBERED_LIST_ITEM,
    PARAGRAPH,
    PDF,
    QUOTE,
    SYNCED_BLOCK,
    TABLE,
    TABLE_OF_CONTENTS,
    TABLE_ROW,
    TEMPLATE,
    TO_DO,
    TOGGLE,
    UNSUPPORTED,
    VIDEO;

    public static NotionBlockType findBlockType(final String type) {
        return Arrays.stream(NotionBlockType.values())
                .filter(blockType -> blockType.name().toLowerCase().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 블록 타입입니다."));
    }
}
