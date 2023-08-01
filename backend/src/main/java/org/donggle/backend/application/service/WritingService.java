package org.donggle.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.BlockRepository;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.notion.NotionApiService;
import org.donggle.backend.application.service.notion.NotionBlockNode;
import org.donggle.backend.application.service.request.MarkdownUploadRequest;
import org.donggle.backend.application.service.request.NotionUploadRequest;
import org.donggle.backend.domain.blog.BlogWriting;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Email;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.member.Password;
import org.donggle.backend.domain.parser.markdown.MarkDownParser;
import org.donggle.backend.domain.parser.markdown.MarkDownStyleParser;
import org.donggle.backend.domain.parser.notion.NotionParser;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.renderer.html.HtmlStyleRenderer;
import org.donggle.backend.domain.writing.Block;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.content.Content;
import org.donggle.backend.exception.business.InvalidFileFormatException;
import org.donggle.backend.exception.notfound.CategoryNotFoundException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.ui.response.PublishedDetailResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.donggle.backend.ui.response.WritingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WritingService {
    private static final String MD_FORMAT = ".md";
    private final NotionApiService notionApiService;
    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;
    private final WritingRepository writingRepository;
    private final BlogWritingRepository blogWritingRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long uploadMarkDownFile(final Long memberId, final MarkdownUploadRequest request) throws IOException {
        final String originalFilename = request.file().getOriginalFilename();
        if (!Objects.requireNonNull(originalFilename).endsWith(MD_FORMAT)) {
            throw new InvalidFileFormatException();
        }
        final String originalFileText = new String(request.file().getBytes(), StandardCharsets.UTF_8);

        final MarkDownParser markDownParser = new MarkDownParser(new MarkDownStyleParser());
        // TODO : authentication 후 member 객체 가져오도록 수정
        final Member member = new Member(new MemberName("동그리"), new Email("a@a.com"), new Password("1234"));
        final Member savedMember = memberRepository.save(member);
        final Category findCategory = findCategory(request.categoryId());
        final Writing writing = new Writing(savedMember, new Title(findFileName(originalFilename)), findCategory);
        final Writing savedWriting = writingRepository.save(writing);

        final List<Content> contents = markDownParser.parse(originalFileText);
        //TODO : CASCADE 추가
        final List<Block> blocks = contents.stream()
                .map(content -> new Block(savedWriting, content))
                .toList();
        blockRepository.saveAll(blocks);

        return savedWriting.getId();
    }

    private String findFileName(final String originalFilename) {
        final int endIndex = Objects.requireNonNull(originalFilename).lastIndexOf(MD_FORMAT);
        return originalFilename.substring(0, endIndex);
    }

    public Long uploadNotionPage(final Long memberId, final NotionUploadRequest request) {
        // TODO : authentication 후 member 객체 가져오도록 수정
        final Member member = new Member(new MemberName("동그리"), new Email("a@a.com"), new Password("1234"));
        final Member savedMember = memberRepository.save(member);
        final Category findCategory = findCategory(request.categoryId());

        final String blockId = request.blockId();
        final NotionParser notionParser = new NotionParser();

        final NotionBlockNode parentBlockNode = notionApiService.retrieveParentBlockNode(blockId);
        final String title = notionParser.parseTitle(parentBlockNode);
        final Writing writing = new Writing(savedMember, new Title(title), findCategory);

        final Writing savedWriting = writingRepository.save(writing);

        final List<NotionBlockNode> bodyBlockNodes = notionApiService.retrieveBodyBlockNodes(parentBlockNode);
        final List<Content> contents = notionParser.parseBody(bodyBlockNodes);
        final List<Block> blocks = contents.stream()
                .map(content -> new Block(savedWriting, content))
                .toList();
        blockRepository.saveAll(blocks);

        return writing.getId();
    }

    private Category findCategory(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public WritingResponse findWriting(final Long memberId, final Long writingId) {
        final HtmlRenderer htmlRenderer = new HtmlRenderer(new HtmlStyleRenderer());
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Writing writing = findWriting(writingId);
        final List<Block> blocks = blockRepository.findAllByWritingId(writingId);

        final String content = htmlRenderer.render(blocks);

        return new WritingResponse(writing.getId(), writing.getTitleValue(), content);
    }

    private Writing findWriting(final Long writingId) {
        return writingRepository.findById(writingId)
                .orElseThrow(() -> new WritingNotFoundException(writingId));
    }

    public WritingPropertiesResponse findWritingProperties(final Long memberId, final Long writingId) {
        // TODO : authentication 후 member 객체 가져오도록 수정 후 검증 로직 추가
        final Writing writing = findWriting(writingId);
        final List<BlogWriting> blogWritings = blogWritingRepository.findByWritingId(writingId);
        final List<PublishedDetailResponse> publishedTos = blogWritings.stream()
                .map(blogWriting -> new PublishedDetailResponse(
                        blogWriting.getBlogTypeValue(),
                        blogWriting.getPublishedAt())
                )
                .toList();

        return new WritingPropertiesResponse(writing.getCreatedAt(), publishedTos);
    }
}
