package org.donggle.backend.domain.parser;

import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.content.Block;
import org.donggle.backend.domain.writing.content.CodeBlock;
import org.donggle.backend.domain.writing.content.Depth;
import org.donggle.backend.domain.writing.content.ImageBlock;
import org.donggle.backend.domain.writing.content.ImageCaption;
import org.donggle.backend.domain.writing.content.ImageUrl;
import org.donggle.backend.domain.writing.content.Language;
import org.donggle.backend.domain.writing.content.NormalBlock;
import org.donggle.backend.domain.writing.content.RawText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MarkDownParserTest {
    private MarkDownParser markDownParser;

    @BeforeEach
    void setUp() {
        markDownParser = new MarkDownParser(new MarkDownStyleParser());
    }

    @Test
    @DisplayName("""
            ###안녕
              안녕
            ```java
            나는 자바다
            ```
            의 Contents를 정상적으로 구하는 테스트""")
    void createNormalContentFromTextBlock1() {
        //given
        final String text = """
                ###안녕
                  안녕
                ```java
                나는 자바다
                ```
                """;

        final List<Block> result = List.of(
                new NormalBlock(Depth.empty(), BlockType.PARAGRAPH, RawText.from("###안녕"), Collections.emptyList()),
                new NormalBlock(Depth.empty(), BlockType.PARAGRAPH, RawText.from("  안녕"), Collections.emptyList()),
                new CodeBlock(BlockType.CODE_BLOCK, RawText.from("나는 자바다"), Language.from("java"))
        );

        //when
        final List<Block> parse = markDownParser.parse(text);

        //then
        assertThat(parse).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(result);
    }

    @Test
    @DisplayName("이미지 파싱 테스트")
    void parserImage() {
        //given
        final String text = "![imageName](www.naver.com)";
        final ImageBlock expected = new ImageBlock(BlockType.IMAGE, new ImageUrl("www.naver.com"), new ImageCaption("imageName"));

        //when
        final List<Block> result = markDownParser.parse(text);
        final ImageBlock resultContent = (ImageBlock) result.get(0);

        //then
        assertAll(
                () -> assertThat(resultContent.getDepthValue()).isEqualTo(expected.getDepthValue()),
                () -> assertThat(resultContent.getImageUrlValue()).isEqualTo(expected.getImageUrlValue()),
                () -> assertThat(resultContent.getImageCaptionValue()).isEqualTo(expected.getImageCaptionValue())
        );
    }

    @Nested
    @DisplayName("depth 파싱을 테스트한다.")
    class ParseDepth {
        @Test
        @DisplayName("공백 4개")
        void parseDepth() {
            //given
            final String text = "    - hello world\n        - hubcreator";
            final NormalBlock expected1 = new NormalBlock(Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("hello world"), List.of());
            final NormalBlock expected2 = new NormalBlock(Depth.from(2), BlockType.UNORDERED_LIST, RawText.from("hubcreator"), List.of());

            //when
            final List<Block> result = markDownParser.parse(text);

            //then
            assertAll(
                    () -> assertThat(result.get(0).getDepthValue()).isEqualTo(expected1.getDepthValue()),
                    () -> assertThat(result.get(1).getDepthValue()).isEqualTo(expected2.getDepthValue())
            );
        }

        @Test
        @DisplayName("탭")
        void parseDepth2() {
            //given
            final String text = "\t- hello world\n\t\t - hubcreator";
            final NormalBlock expected1 = new NormalBlock(Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("hello world"), List.of());
            final NormalBlock expected2 = new NormalBlock(Depth.from(2), BlockType.UNORDERED_LIST, RawText.from("hubcreator"), List.of());

            //when
            final List<Block> result = markDownParser.parse(text);

            //then
            assertAll(
                    () -> assertThat(result.get(0).getDepthValue()).isEqualTo(expected1.getDepthValue()),
                    () -> assertThat(result.get(1).getDepthValue()).isEqualTo(expected2.getDepthValue())
            );
        }

        @Test
        @DisplayName("공백 4개와 탭이 섞여있는 경우")
        void parseDepthWithListMixed() {
            //given
            final String text = "- depth1\n\t- depth2\n    \t- depth3\n    \t    - depth4";
            final NormalBlock expected1 = new NormalBlock(Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("depth1"), List.of());
            final NormalBlock expected2 = new NormalBlock(Depth.from(1), BlockType.UNORDERED_LIST, RawText.from("depth2"), List.of());
            final NormalBlock expected3 = new NormalBlock(Depth.from(2), BlockType.UNORDERED_LIST, RawText.from("depth3"), List.of());
            final NormalBlock expected4 = new NormalBlock(Depth.from(3), BlockType.UNORDERED_LIST, RawText.from("depth4"), List.of());

            //when
            final List<Block> result = markDownParser.parse(text);

            //then
            assertAll(
                    () -> assertThat(result.get(0).getDepthValue()).isEqualTo(expected1.getDepthValue()),
                    () -> assertThat(result.get(1).getDepthValue()).isEqualTo(expected2.getDepthValue()),
                    () -> assertThat(result.get(2).getDepthValue()).isEqualTo(expected3.getDepthValue()),
                    () -> assertThat(result.get(3).getDepthValue()).isEqualTo(expected4.getDepthValue())
            );
        }

        @Test
        @DisplayName("공백 4개와 탭이 섞여있고, 중간에 공백 4개가 있는 경우")
        void parseDepthWithListMixed2() {
            //given
            final String text = "- depth1    hel\tlo\n";
            final NormalBlock expected1 = new NormalBlock(Depth.empty(), BlockType.UNORDERED_LIST, RawText.from("depth1    hel\tlo"), List.of());

            //when
            final List<Block> result = markDownParser.parse(text);
            final NormalBlock resultContent = (NormalBlock) result.get(0);

            //then
            assertAll(
                    () -> assertThat(resultContent.getDepthValue()).isEqualTo(expected1.getDepthValue()),
                    () -> assertThat(resultContent.getRawTextValue()).isEqualTo(expected1.getRawTextValue())
            );
        }
    }
}
