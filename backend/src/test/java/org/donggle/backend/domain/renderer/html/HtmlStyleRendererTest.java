package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlStyleRendererTest {
    private HtmlStyleRenderer htmlStyleRenderer;
    private List<Style> styles;

    @BeforeEach
    void setUp() {
        htmlStyleRenderer = new HtmlStyleRenderer();
        styles = new ArrayList<>();
    }

    @Test
    @DisplayName("Bold 스타일 렌더링")
    void renderBold() {
        //given
        String rawText = "안녕하세요 동글입니다.";
        styles.add(new Style(0, 5, StyleType.BOLD));

        //when
        String result = htmlStyleRenderer.render(rawText, styles);
        String expected = "<strong>안녕하세요 </strong>동글입니다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Italic 스타일 렌더링")
    void renderItalic() {
        //given
        String rawText = "안녕하세요 동글입니다.";
        styles.add(new Style(3, 9, StyleType.ITALIC));

        //when
        String result = htmlStyleRenderer.render(rawText, styles);
        String expected = "안녕하<em>세요 동글입니</em>다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Code 스타일 렌더링")
    void renderCode() {
        //given
        String rawText = "안녕하세요 동글입니다.";
        styles.add(new Style(2, 5, StyleType.CODE));

        //when
        String result = htmlStyleRenderer.render(rawText, styles);
        String expected = "안녕<code>하세요 </code>동글입니다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("스타일 렌더링")
    void renderStyle() {
        //given
        String rawText = "안녕하세요 동글입니다.";
        List<Style> styles = new ArrayList<>();
        styles.add(new Style(0, 5, StyleType.BOLD));
        styles.add(new Style(3, 5, StyleType.CODE));
        styles.add(new Style(7, 9, StyleType.ITALIC));

        //when
        String result = htmlStyleRenderer.render(rawText, styles);
        String expected = "<strong>안녕하<code>세요 </strong></code>동<em>글입니</em>다.";

        //then
        assertThat(result).isEqualTo(expected);
    }
}
