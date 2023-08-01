package org.donggle.backend.domain.parser.notion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnnotationsTest {
    private static final JsonNode TEST_JSON_NODE = JsonNodeFactory.instance.objectNode()
            .put("bold", true)
            .put("italic", true)
            .put("strikethrough", true)
            .put("underline", true)
            .put("code", true)
            .put("color", "default");

    @Test
    @DisplayName("어노테이션을 파싱하는 테스트")
    void parseAnnotations() {
        //given
        //when
        final Annotations annotations = Annotations.from(TEST_JSON_NODE);
        //then
        final Annotations expected = new Annotations(true, true, true, true, true, "default");
        Assertions.assertThat(annotations).isEqualTo(expected);
    }

    @Test
    @DisplayName("빈 어노테이션을 생성하는 테스트")
    void empty() {
        //given
        //when
        final Annotations result = Annotations.empty();
        final Annotations expected = new Annotations(false, false, false, false, false, "default");
        
        //then
        Assertions.assertThat(result).isEqualTo(expected);

    }
}