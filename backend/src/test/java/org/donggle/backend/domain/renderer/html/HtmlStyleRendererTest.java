package org.donggle.backend.domain.renderer.html;

import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
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
        final String rawText = "안녕하세요 동글입니다.";
        styles.add(new Style(new StyleRange(0, 5), StyleType.BOLD));

        //when
        final String result = htmlStyleRenderer.render(rawText, styles);
        final String expected = "<strong>안녕하세요 </strong>동글입니다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Italic 스타일 렌더링")
    void renderItalic() {
        //given
        final String rawText = "안녕하세요 동글입니다.";
        styles.add(new Style(new StyleRange(3, 9), StyleType.ITALIC));

        //when
        final String result = htmlStyleRenderer.render(rawText, styles);
        final String expected = "안녕하<em>세요 동글입니</em>다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Code 스타일 렌더링")
    void renderCode() {
        //given
        final String rawText = "안녕하세요 동글입니다.";
        styles.add(new Style(new StyleRange(2, 5), StyleType.CODE));

        //when
        final String result = htmlStyleRenderer.render(rawText, styles);
        final String expected = "안녕<code>하세요 </code>동글입니다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("스타일 렌더링")
    void renderStyle() {
        //given
        final String rawText = "안녕하세요 동글입니다.";
        final List<Style> styles = new ArrayList<>();
        styles.add(new Style(new StyleRange(0, 5), StyleType.BOLD));
        styles.add(new Style(new StyleRange(3, 5), StyleType.CODE));
        styles.add(new Style(new StyleRange(7, 9), StyleType.ITALIC));

        //when
        final String result = htmlStyleRenderer.render(rawText, styles);
        final String expected = "<strong>안녕하<code>세요 </strong></code>동<em>글입니</em>다.";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("링크 스타일 렌더링")
    void renderLink() {
        //given
        final String rawText = "link.com캡션";
        final List<Style> styles = new ArrayList<>();
        styles.add(new Style(new StyleRange(0, 7), StyleType.LINK));
        styles.add(new Style(new StyleRange(8, 9), StyleType.LINK));

        //when
        final String result = htmlStyleRenderer.render(rawText, styles);
        final String expected = "<a href=\"link.com\">캡션</a>";

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("빈 캡션 링크 테스트")
    void renderEmptyCaptionLink() {
        //given
        final String rawText = "link.com";
        final List<Style> styles = new ArrayList<>();
        styles.add(new Style(new StyleRange(0, 0), StyleType.LINK));
        styles.add(new Style(new StyleRange(0, 7), StyleType.LINK));

        //when
        final String result = htmlStyleRenderer.render(rawText, styles);
        final String expected = "<a href=\"link.com\"></a>";


        //then
        assertThat(result).isEqualTo(expected);

    }
}
