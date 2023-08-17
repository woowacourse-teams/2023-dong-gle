package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.application.service.vendor.notion.NotionApiService;
import org.donggle.backend.application.service.vendor.notion.dto.NotionBlockNode;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.parser.notion.NotionParser;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.exception.business.InvalidFileFormatException;
import org.donggle.backend.exception.business.NotionNotConnectedException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.PublishedDetailResponse;
import org.donggle.backend.ui.response.WritingDetailResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class WritingService {
    private static final String MD_FORMAT = ".md";
    private static final int LAST_WRITING_FLAG = -1;

    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;
    private final WritingRepository writingRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final CategoryRepository categoryRepository;

    public Long uploadMarkDownFile(final Long memberId, final MarkdownUploadRequest request) throws IOException {
        final String originalFilename = request.file().getOriginalFilename();
        if (!Objects.requireNonNull(originalFilename).endsWith(MD_FORMAT)) {
            throw new InvalidFileFormatException(originalFilename);
        }
        final String originalFileText = new String(request.file().getBytes(), StandardCharsets.UTF_8);

        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), request.categoryId());
        final Writing writing = Writing.lastOf(findMember, new Title(findFileName(originalFilename)), findCategory);
        final Writing savedWriting = saveAndGetWriting(findCategory, writing);
        final MarkDownParser markDownParser = new MarkDownParser(new MarkDownStyleParser(), savedWriting);

        final List<Block> blocks = markDownParser.parse(originalFileText);
        blockRepository.saveAll(blocks);
        return savedWriting.getId();
    }

    private String findFileName(final String originalFilename) {
        final int endIndex = Objects.requireNonNull(originalFilename).lastIndexOf(MD_FORMAT);
        return originalFilename.substring(0, endIndex);
    }

    public Long uploadNotionPage(final Long memberId, final NotionUploadRequest request) {
        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), request.categoryId());
        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(findMember).orElseThrow();
        final String notionToken = memberCredentials.getNotionToken()
                .orElseThrow(NotionNotConnectedException::new);
        final NotionApiService notionApiService = new NotionApiService();

        final String blockId = request.blockId();
        final NotionBlockNode parentBlockNode = notionApiService.retrieveParentBlockNode(blockId, notionToken);
        final String title = findTitle(parentBlockNode);
        final Writing writing = Writing.lastOf(findMember, new Title(title), findCategory);
        final Writing savedWriting = saveAndGetWriting(findCategory, writing);
        final NotionParser notionParser = new NotionParser(savedWriting);

        final List<NotionBlockNode> bodyBlockNodes = notionApiService.retrieveBodyBlockNodes(parentBlockNode, notionToken);
        final List<Block> blocks = notionParser.parseBody(bodyBlockNodes);
        blockRepository.saveAll(blocks);
        return writing.getId();
    }

    private String findTitle(final NotionBlockNode parentBlockNode) {
        return parentBlockNode.getBlockProperties().get("title").asText();
    }

    private Writing saveAndGetWriting(final Category findCategory, final Writing writing) {
        if (isNotEmptyCategory(findCategory)) {
            final Writing lastWriting = findLastWritingInCategory(findCategory.getId());
            final Writing savedWriting = writingRepository.save(writing);
            lastWriting.changeNextWriting(savedWriting);
            return savedWriting;
        }
        return writingRepository.save(writing);
    }

    private boolean isNotEmptyCategory(final Category category) {
        return writingRepository.countByCategoryId(category.getId()) != 0;
    }

    public void modifyWritingTitle(final Long memberId, final Long writingId, final WritingModifyRequest request) {
        final Writing findWriting = findWritingById(memberId, writingId);
        findWriting.updateTitle(new Title(request.title()));
    }

    @Transactional(readOnly = true)
    public WritingResponse findWriting(final Long memberId, final Long writingId) {
        final HtmlRenderer htmlRenderer = new HtmlRenderer(new HtmlStyleRenderer());
        final Writing writing = findWritingById(memberId, writingId);
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);
        final String content = htmlRenderer.render(blocks);
        return WritingResponse.of(writing, content);
    }

    @Transactional(readOnly = true)
    public WritingPropertiesResponse findWritingProperties(final Long memberId, final Long writingId) {
        final Writing writing = findWritingById(memberId, writingId);
        final List<PublishedDetailResponse> publishedTos = convertToPublishedDetailResponses(writingId);
        return WritingPropertiesResponse.of(writing, publishedTos);
    }

    @Transactional(readOnly = true)
    public WritingListWithCategoryResponse findWritingListByCategoryId(final Long memberId, final Long categoryId) {
        final Category findCategory = findCategory(memberId, categoryId);
        final List<Writing> findWritings = writingRepository.findAllByCategoryId(findCategory.getId());
        if (findWritings.isEmpty()) {
            return WritingListWithCategoryResponse.of(findCategory, Collections.emptyList());
        }
        final Writing firstWriting = findFirstWriting(findWritings);
        final List<Writing> sortedWriting = sortWriting(findWritings, firstWriting);
        final List<WritingDetailResponse> writingDetailResponses = sortedWriting.stream()
                .map(writing -> WritingDetailResponse.of(writing, convertToPublishedDetailResponses(writing.getId())))
                .toList();
        return WritingListWithCategoryResponse.of(findCategory, writingDetailResponses);
    }

    private Writing findFirstWriting(final List<Writing> findWritings) {
        final List<Writing> copy = new ArrayList<>(findWritings);
        final List<Writing> nextWritings = findWritings.stream()
                .map(Writing::getNextWriting)
                .toList();
        copy.removeAll(nextWritings);
        return copy.get(0);
    }

    private List<Writing> sortWriting(final List<Writing> writings, Writing targetWriting) {
        final Map<Writing, Writing> writingMap = new LinkedHashMap<>();
        for (final Writing writing : writings) {
            writingMap.put(writing, writing.getNextWriting());
        }
        final List<Writing> sortedWritings = new ArrayList<>();
        sortedWritings.add(targetWriting);
        while (Objects.nonNull(targetWriting.getNextWriting())) {
            targetWriting = writingMap.get(targetWriting);
            sortedWritings.add(targetWriting);
        }
        return sortedWritings;
    }

    public void modifyWritingOrder(final Long memberId, final Long writingId, final WritingModifyRequest request) {
        final Long nextWritingId = request.nextWritingId();
        final Long targetCategoryId = request.targetCategoryId();

        final Writing source = findWritingById(memberId, writingId);
        deleteWritingOrder(source);
        addWritingOrder(memberId, targetCategoryId, nextWritingId, source);

        changeCategory(memberId, targetCategoryId, source);
    }

    private void deleteWritingOrder(final Writing writing) {
        final Writing nextWriting = writing.getNextWriting();
        writing.changeNextWritingNull();

        if (isNotFirstWriting(writing.getId())) {
            final Writing preWriting = findPreWriting(writing.getId());
            preWriting.changeNextWriting(nextWriting);
        }
    }

    private void addWritingOrder(final Long memberId, final Long categoryId, final Long nextWritingId, final Writing writing) {
        if (isNotFirstWriting(nextWritingId)) {
            final Writing preWriting;
            if (nextWritingId == LAST_WRITING_FLAG) {
                preWriting = findLastWritingInCategory(categoryId);
            } else {
                preWriting = findPreWriting(nextWritingId);
            }
            preWriting.changeNextWriting(writing);
        }
        if (nextWritingId != LAST_WRITING_FLAG) {
            final Writing nextWriting = findWritingById(memberId, nextWritingId);
            writing.changeNextWriting(nextWriting);
        }
    }

    private boolean isNotFirstWriting(final Long writingId) {
        return writingRepository.countByNextWritingId(writingId) != 0;
    }

    private void changeCategory(final Long memberId, final Long categoryId, final Writing writing) {
        final Category sourceCategory = writing.getCategory();
        final Category targetCategory = findCategory(memberId, categoryId);
        if (!targetCategory.equals(sourceCategory)) {
            writing.changeCategory(targetCategory);
        }
    }

    private List<PublishedDetailResponse> convertToPublishedDetailResponses(final Long findWriting) {
        final List<BlogWriting> blogWritings = blogWritingRepository.findByWritingId(findWriting);
        return blogWritings.stream()
                .map(blogWriting -> new PublishedDetailResponse(
                        blogWriting.getBlogTypeValue(),
                        blogWriting.getPublishedAt(),
                        blogWriting.getTags()))
                .toList();
    }

    private Category findCategory(final Long memberId, final Long categoryId) {
        return categoryRepository.findByIdAndMemberId(categoryId, memberId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private Writing findLastWritingInCategory(final Long categoryId) {
        return writingRepository.findLastWritingByCategoryId(categoryId)
                .orElseThrow(IllegalStateException::new);
    }

    private Writing findWritingById(final Long memberId, final Long writingId) {
        return writingRepository.findByMemberIdAndId(memberId, writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
    }

    private Writing findPreWriting(final Long writingId) {
        return writingRepository.findPreWritingByWritingId(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
