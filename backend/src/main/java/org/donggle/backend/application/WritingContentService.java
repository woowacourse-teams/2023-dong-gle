package org.donggle.backend.application;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.MemberCategoryNotionInfo;
import org.donggle.backend.application.service.WritingService;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.notion.NotionParser;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.exception.business.InvalidFileFormatException;
import org.donggle.backend.infrastructure.client.notion.NotionApiClient;
import org.donggle.backend.infrastructure.client.notion.dto.response.NotionBlockNodeResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WritingContentService {
    private static final String MD_FORMAT = ".md";

    private final WritingService writingService;
    private final MarkDownParser markDownParser;
    private final NotionParser notionParser;
    private final HtmlRenderer htmlRenderer;

    public Long uploadMarkDownFile(final Long memberId, final MarkdownUploadRequest request) throws IOException {
        final String originalFilename = request.file().getOriginalFilename();
        if (!Objects.requireNonNull(originalFilename).endsWith(MD_FORMAT)) {
            throw new InvalidFileFormatException(originalFilename);
        }
        final String originalFileText = new String(request.file().getBytes(), StandardCharsets.UTF_8);
        final List<Block> blocks = markDownParser.parse(originalFileText);

        return writingService.saveByFile(memberId, request.categoryId(), originalFilename, blocks);
    }

    public Long uploadNotionPage(final Long memberId, final NotionUploadRequest request) {
        final NotionApiClient notionApiService = new NotionApiClient();
        final MemberCategoryNotionInfo memberCategoryNotionInfo = writingService.getMemberCategoryNotionInfo(memberId, request.categoryId());
        final Member member = memberCategoryNotionInfo.member();
        final Category category = memberCategoryNotionInfo.category();
        final String notionToken = memberCategoryNotionInfo.notionToken();
        final NotionBlockNodeResponse parentBlockNode = notionApiService.retrieveParentBlockNode(request.blockId(), notionToken);
        final List<NotionBlockNodeResponse> bodyBlockNodes = notionApiService.retrieveBodyBlockNodes(parentBlockNode, notionToken);
        final String title = notionApiService.findTitle(parentBlockNode);
        final List<Block> blocks = notionParser.parseBody(bodyBlockNodes);
        final Writing writing = Writing.of(member, new Title(title), category, blocks);
        return writingService.saveAndGetWriting(category, writing).getId();
    }

    public WritingResponse findWriting(final Long memberId, final Long writingId) {
        final Writing writing = writingService.findWriting(memberId, writingId);
        final String content = htmlRenderer.render(writing.getBlocks());
        return WritingResponse.of(writing, content);
    }

    public void modifyWritingTitle(final Long memberId, final Long writingId, final WritingModifyRequest request) {
        final Title title = new Title(request.title());
        writingService.modifyWritingTitle(memberId, writingId, title);
    }

    public void modifyWritingOrder(final Long memberId, final Long writingId, final WritingModifyRequest request) {
        writingService.modifyWritingOrder(memberId, writingId, request);
    }

    public WritingPropertiesResponse findWritingProperties(final Long memberId, final Long writingId) {
        return writingService.findWritingProperties(memberId, writingId);
    }

    public WritingListWithCategoryResponse findWritingListByCategoryId(final Long memberId, final Long categoryId) {
        return writingService.findWritingListByCategoryId(memberId, categoryId);
    }
}
