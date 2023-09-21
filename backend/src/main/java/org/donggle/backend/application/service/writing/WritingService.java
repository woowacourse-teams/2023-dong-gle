package org.donggle.backend.application.service.writing;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.exception.business.NotionNotConnectedException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.exception.notfound.MemberNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.PublishedDetailResponse;
import org.donggle.backend.ui.response.WritingDetailResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.donggle.backend.domain.writing.BlockType.CODE_BLOCK;
import static org.donggle.backend.domain.writing.BlockType.HORIZONTAL_RULES;
import static org.donggle.backend.domain.writing.BlockType.IMAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class WritingService {
    private static final String MD_FORMAT = ".md";
    private static final int LAST_WRITING_FLAG = -1;

    private final MemberRepository memberRepository;
    private final WritingRepository writingRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;
    private final CategoryRepository categoryRepository;

    public Long saveByFile(final Long memberId, final Long categoryId, final String title, final List<Block> blocks) {
        final Member findMember = findMember(memberId);
        final Category findCategory = findCategory(findMember.getId(), categoryId);
        final Writing writing = Writing.of(findMember, new Title(findFileName(title)), findCategory, blocks);
        final Writing savedWriting = saveAndGetWriting(findCategory, writing);

        return savedWriting.getId();
    }

    private String findFileName(final String originalFilename) {
        final int endIndex = Objects.requireNonNull(originalFilename).lastIndexOf(MD_FORMAT);
        return originalFilename.substring(0, endIndex);
    }

    public MemberCategoryNotionInfo getMemberCategoryNotionInfo(final Long memberId, final Long categoryId) {
        final Member findMember = findMember(memberId);
        final Category category = findCategory(memberId, categoryId);
        final MemberCredentials memberCredentials = memberCredentialsRepository.findMemberCredentialsByMember(findMember).orElseThrow();
        final String notionToken = memberCredentials.getNotionToken()
                .orElseThrow(NotionNotConnectedException::new);
        return new MemberCategoryNotionInfo(findMember, category, notionToken);
    }

    public Writing saveAndGetWriting(final Category findCategory, final Writing writing) {
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

    public void modifyWritingTitle(final Long memberId, final Long writingId, final Title title) {
        final Writing findWriting = findWritingById(writingId);
        validateAuthorization(memberId, findWriting);
        findWriting.updateTitle(title);
    }

    @Transactional(readOnly = true)
    public Writing findWritingWithBlocks(final Long memberId, final Long writingId) {
        final Writing writing = writingRepository.findByMemberIdAndWritingIdAndStatusIsNotDeletedWithBlocks(memberId, writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
        findStyleByNomalBlocks(writing);
        return writing;
    }

    private void findStyleByNomalBlocks(final Writing writing) {
        final List<Block> blocks = writing.getBlocks();
        final Set<BlockType> notNormalType = Set.of(CODE_BLOCK, IMAGE, HORIZONTAL_RULES);
        final List<NormalBlock> normalBlocks = blocks.stream()
                .filter(block -> !notNormalType.contains(block.getBlockType()))
                .map(NormalBlock.class::cast)
                .toList();
        writingRepository.findStylesForBlocks(normalBlocks);
    }

    @Transactional(readOnly = true)
    public WritingPropertiesResponse findWritingProperties(final Long memberId, final Long writingId) {
        final Writing writing = findWritingAndTrashedWriting(memberId, writingId);
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
        final Map<Writing, List<BlogWriting>> blogWritings = blogWritingRepository.findWithFetch(sortedWriting)
                .stream()
                .collect(Collectors.groupingBy(BlogWriting::getWriting));

        final List<WritingDetailResponse> responses = sortedWriting.stream()
                .map(writing -> {
                    final List<PublishedDetailResponse> publishedDetailResponses = Optional.ofNullable(blogWritings.get(writing))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(PublishedDetailResponse::of)
                            .toList();
                    return WritingDetailResponse.of(writing, publishedDetailResponses);
                })
                .toList();

        return WritingListWithCategoryResponse.of(findCategory, responses);
    }

    private Writing findFirstWriting(final List<Writing> findWritings) {
        final List<Writing> copy = new ArrayList<>(findWritings);
        final List<Writing> nextWritings = findWritings.stream()
                .map(Writing::getNextWriting)
                .toList();
        copy.removeAll(nextWritings);
        return copy.get(0);
    }

    private List<Writing> sortWriting(final List<Writing> writings, final Writing firstWriting) {
        final Map<Writing, Writing> writingMap = new LinkedHashMap<>();
        for (final Writing writing : writings) {
            writingMap.put(writing, writing.getNextWriting());
        }
        final List<Writing> sortedWritings = new ArrayList<>();
        sortedWritings.add(firstWriting);
        Writing targetWriting = firstWriting;
        while (Objects.nonNull(targetWriting.getNextWriting())) {
            targetWriting = writingMap.get(targetWriting);
            sortedWritings.add(targetWriting);
        }
        return sortedWritings;
    }

    public void modifyWritingOrder(final Long memberId, final Long writingId, final WritingModifyRequest request) {
        final Long nextWritingId = request.nextWritingId();
        final Long targetCategoryId = request.targetCategoryId();
        final Writing source = findWritingById(writingId);
        validateAuthorization(memberId, source);
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
            final Writing nextWriting = findWritingById(nextWritingId);
            validateAuthorization(memberId, nextWriting);
            writing.changeNextWriting(nextWriting);
        }
    }

    private void validateAuthorization(final Long memberId, final Writing writing) {
        if (!writing.isOwnedBy(memberId)) {
            throw new WritingNotFoundException(writing.getId());
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
                .map(PublishedDetailResponse::of)
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

    private Writing findWritingById(final Long writingId) {
        return writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
    }

    private Writing findWritingAndTrashedWriting(final Long memberId, final Long writingId) {
        return writingRepository.findByMemberIdAndWritingIdAndStatusIsNotDeleted(memberId, writingId)
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
