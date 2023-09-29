package org.donggle.backend.application.service;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.service.parse.NotionParseService;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.application.service.writing.WritingFacadeService;
import org.donggle.backend.application.service.writing.WritingService;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;
import org.donggle.backend.exception.business.InvalidFileFormatException;
import org.donggle.backend.support.fix.CategoryFixture;
import org.donggle.backend.support.fix.WritingFixture;
import org.donggle.backend.ui.response.PublishedDetailResponse;
import org.donggle.backend.ui.response.WritingDetailResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.support.fix.MemberFixture.beaver;
import static org.donggle.backend.support.fix.WritingFixture.writing_ACTIVE;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WritingFacadeServiceTest {
    @InjectMocks
    private WritingFacadeService writingFacadeService;
    @Mock
    private WritingService writingService;
    @Mock
    private NotionParseService notionParser;
    @Mock
    private MarkDownParser markDownParser;
    @Mock
    private HtmlRenderer htmlRenderer;

    @Test
    @DisplayName("정상적인 file업로드 테스트")
    void uploadMarkDownFileTest_md() throws IOException {
        // given
        final Long memberId = 1L;
        final Long categoryId = 1L;
        final String originalFilename = "test.md";
        final String content = "content";
        final MultipartFile multipartFile = new MockMultipartFile("file", originalFilename, "text/plain", content.getBytes());
        final MarkdownUploadRequest request = new MarkdownUploadRequest(multipartFile, categoryId);

        final List<Block> blocks = List.of(new NormalBlock(Depth.from(-1), BlockType.PARAGRAPH, RawText.from("content"), List.of()));
        given(markDownParser.parse(content)).willReturn(blocks);
        given(writingService.saveByFile(memberId, categoryId, originalFilename, blocks)).willReturn(1L);

        // when
        final Long result = writingFacadeService.uploadMarkDownFile(memberId, request);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("md형식이 아닌 file업로드 에러")
    void uploadMarkDownFileTest_pdf() {
        // given
        final Long memberId = 1L;
        final Long categoryId = 1L;
        final String originalFilename = "test.pdf";
        final String content = "content";
        final MultipartFile multipartFile = new MockMultipartFile("file", originalFilename, "text/plain", content.getBytes());
        final MarkdownUploadRequest request = new MarkdownUploadRequest(multipartFile, categoryId);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> writingFacadeService.uploadMarkDownFile(memberId, request)
        ).isInstanceOf(InvalidFileFormatException.class);
    }

    @Test
    @DisplayName("글 제목 수정 테스트")
    void modifyWritingTitle() {
        //given
        final long memberId = 1L;
        final long writingId = 1L;
        final Title title = new Title("글 제목 수정 API 테스트");
        final WritingModifyRequest request = new WritingModifyRequest("글 제목 수정 API 테스트", null, null);

        //when
        writingFacadeService.modifyWritingTitle(memberId, writingId, request);

        //then
        then(writingService).should(times(1)).modifyWritingTitle(memberId, writingId, title);
    }

    @Test
    @DisplayName("카테고리에 해당하는 글의 목록 조회 테스트")
    void findWritingListByCategoryId() {
        //given
        final List<Writing> writings = WritingFixture.createWritings_ACTIVE();
        final Category basicCategory = CategoryFixture.basicCategory;
        final List<WritingDetailResponse> responses = writings.stream()
                .map(writing -> {
                    final List<PublishedDetailResponse> publishedDetailResponses = List.of();
                    return WritingDetailResponse.of(writing, publishedDetailResponses);
                }).toList();
        given(writingService.findWritingListByCategoryId(beaver.getId(), basicCategory.getId())).willReturn(WritingListWithCategoryResponse.of(basicCategory, responses));
        //when
        final WritingListWithCategoryResponse response = writingFacadeService.findWritingListByCategoryId(10L, 1L);

        //then
        assertThat(response.writings()).hasSize(2);
        assertThat(response.categoryName()).isEqualTo("기본");
    }

    @Test
    @DisplayName("글을 조회하는 테스트")
    void findWritingAndTrashedWriting() {
        //given
        final long memberId = 10L;
        final long writingId = 1L;

        given(writingService.findWritingWithBlocks(memberId, writingId)).willReturn(writing_ACTIVE);

        //when
        final WritingResponse response = writingFacadeService.findWriting(memberId, writingId);

        //then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.categoryId()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("Title 1");
    }
}
