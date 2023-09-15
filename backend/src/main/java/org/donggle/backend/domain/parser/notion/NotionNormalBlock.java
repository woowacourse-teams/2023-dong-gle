package org.donggle.backend.domain.parser.notion;

import org.donggle.backend.domain.writing.Style;

import java.util.List;

public interface NotionNormalBlock {
    String parseRawText();

    List<Style> parseStyles();
}
