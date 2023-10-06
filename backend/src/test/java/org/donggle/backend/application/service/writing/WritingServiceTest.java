package org.donggle.backend.application.service.writing;

import org.assertj.core.api.Assertions;
import org.donggle.backend.application.repository.BlogWritingRepository;
import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberCredentialsRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.exception.business.NotionNotConnectedException;
import org.donggle.backend.exception.notfound.WritingNotFoundException;
import org.donggle.backend.support.fix.WritingFixture;
import org.donggle.backend.ui.response.WritingHomeResponse;
import org.donggle.backend.ui.response.WritingListWithCategoryResponse;
import org.donggle.backend.ui.response.WritingPropertiesResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.support.fix.CategoryFixture.basicCategory;
import static org.donggle.backend.support.fix.MemberFixture.beaver_have_id;
import static org.donggle.backend.support.fix.WritingFixture.writing_ACTIVE;
import static org.donggle.backend.support.fix.WritingFixture.writing_DELETED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WritingServiceTest {

    @InjectMocks
    private WritingService writingService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private WritingRepository writingRepository;
    @Mock
    private BlogWritingRepository blogWritingRepository;
    @Mock
    private MemberCredentialsRepository memberCredentialsRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("md file저장 테스트")
    void saveByFile() {
        // given
        final Long memberId = 10L;
        final Long categoryId = 2L;
        final String title = "Test Title.md";
        final List<Block> blocks = Collections.emptyList();

        final Writing writing = Writing.of(beaver_have_id, new Title("Title 1"), basicCategory, blocks);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(basicCategory));
        given(writingRepository.save(writing)).willReturn(WritingFixture.writing_ACTIVE);

        // when
        final Long savedWritingId = writingService.saveByFile(memberId, categoryId, title, blocks);

        // then
        assertThat(savedWritingId).isEqualTo(1L);
    }

    @Test
    @DisplayName("member,category,notionToken의 정보를 불러오는 테스트")
    void getMemberCategoryNotionInfo() {
        // given
        final Long memberId = 1L;
        final Long categoryId = 2L;

        final MemberCredentials memberCredentials = MemberCredentials.basic(beaver_have_id);
        memberCredentials.updateNotionToken("token");
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(basicCategory));
        given(memberCredentialsRepository.findByMember(beaver_have_id)).willReturn(Optional.of(memberCredentials));

        // when
        final MemberCategoryNotionInfo result = writingService.getMemberCategoryNotionInfo(memberId, categoryId);

        // then
        assertThat(result.notionToken()).isEqualTo("token");
        assertThat(result.category()).isEqualTo(basicCategory);
        assertThat(result.member()).isEqualTo(beaver_have_id);
    }

    @Test
    @DisplayName("member,category,notionToken의 정보를 불러올 시 토큰이 없을 때 에러")
    void getMemberCategoryNotionInfo_NotFound_token() {
        // given
        final Long memberId = 1L;
        final Long categoryId = 2L;

        final MemberCredentials memberCredentials = MemberCredentials.basic(beaver_have_id);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(beaver_have_id));
        given(categoryRepository.findByIdAndMemberId(categoryId, memberId)).willReturn(Optional.of(basicCategory));
        given(memberCredentialsRepository.findByMember(beaver_have_id)).willReturn(Optional.of(memberCredentials));

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> writingService.getMemberCategoryNotionInfo(memberId, categoryId)
        ).isInstanceOf(NotionNotConnectedException.class);
    }

    @Test
    @DisplayName("글을 save한 후 저장된 글을 반환하는 테스트")
    void saveAndGetWriting() {
        // given
        final List<Block> blocks = Collections.emptyList();
        final Writing createWriting = Writing.of(beaver_have_id, new Title("Title 1"), basicCategory, blocks);
        final List<Writing> writings = WritingFixture.createWritings_ACTIVE();
        given(writingRepository.countByCategoryIdAndStatus(anyLong(), any())).willReturn(1);
        given(writingRepository.findLastWritingByCategoryId(anyLong())).willReturn(Optional.of(writings.get(1)));
        given(writingRepository.save(createWriting)).willReturn(writing_ACTIVE);

        // when
        final Writing result = writingService.saveAndGetWriting(basicCategory, createWriting);

        // then
        assertThat(result).isEqualTo(writing_ACTIVE);
    }

    @Test
    @DisplayName("카테고리에 있는 글의 정보를 불러오는 테스트")
    void findWritingListByCategoryId() {
        // given
        final Long memberId = 1L;
        final Long categoryId = 1L;

        given(categoryRepository.findByIdAndMemberId(categoryId, memberId))
                .willReturn(Optional.of(basicCategory));
        given(writingRepository.findAllByCategoryIdAndStatus(anyLong(), any()))
                .willReturn(List.of(writing_ACTIVE));

        //when
        final WritingListWithCategoryResponse response = writingService.findWritingListByCategoryId(memberId, categoryId);

        // then
        assertThat(response.writings()).hasSize(1);
        assertThat(response.writings().get(0).title()).isEqualTo("Title 1");
        assertThat(response.categoryName()).isEqualTo("기본");
    }

    @Test
    @DisplayName("글의 정보를 불러오는 테스트")
    void findWritingProperties() {
        // given
        final Long memberId = 1L;
        final Long writingId = 1L;

        given(writingRepository.findByIdAndMemberId(writingId, memberId))
                .willReturn(Optional.of(writing_ACTIVE));

        //when
        final WritingPropertiesResponse response = writingService.findWritingProperties(memberId, writingId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.publishedDetails()).hasSize(0);
    }


    @Test
    @DisplayName("글의 정보를 가져올때 영구삭제된 글이면 에러")
    void findWritingProperties_DELETED() {
        //given
        final Long memberId = 1L;
        final Long writingId = 1L;

        given(writingRepository.findByIdAndMemberId(writingId, memberId))
                .willReturn(Optional.of(writing_DELETED));

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> writingService.findWritingProperties(memberId, writingId)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("글의 내용까지 찾아오는 테스트")
    void findWritingWithBlocks() {
        //given
        final Long memberId = 1L;
        final Long writingId = 1L;

        given(writingRepository.findByWithBlocks(writingId, memberId))
                .willReturn(Optional.of(writing_ACTIVE));

        //when
        final Writing response = writingService.findWritingWithBlocks(memberId, writingId);

        //then
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(writing_ACTIVE);
    }

    @Test
    @DisplayName("글의 내용을 찾을때 writingId의 맞는 글이없을때 에러")
    void findWritingWithBlocks_no_match_id() {
        //given
        final Long memberId = 1L;
        final Long writingId = 1L;

        given(writingRepository.findByWithBlocks(writingId, memberId))
                .willReturn(Optional.empty());


        //when
        //then
        Assertions.assertThatThrownBy(
                () -> writingService.findWritingWithBlocks(memberId, writingId)
        ).isInstanceOf(WritingNotFoundException.class);
    }

    @Test
    @DisplayName("글의 제목 수정 테스트")
    void modifyWritingTitle() {
        //given
        final Long memberId = 10L;
        final Long writingId = 1L;
        final Title title = new Title("New Title");
        final Writing writing = mock(Writing.class);

        given(writing.isOwnedBy(memberId)).willReturn(true);
        given(writingRepository.findByIdAndStatus(writingId, ACTIVE))
                .willReturn(Optional.of(writing));

        //when
        writingService.modifyWritingTitle(memberId, writingId, title);

        //then
        then(writing).should(times(1)).updateTitle(title);
    }

    @Test
    @DisplayName("paging된 글들을 찾는 테스트")
    void findAll_no() {
        //given
        final Long memberId = 10L;
        final PageRequest pageRequest = PageRequest.of(0, 12);
        final Page<Writing> page = new PageImpl<>(WritingFixture.createPageWritings_12());

        given(writingRepository.findByMemberIdAndWritingStatusOrderByCreatedAtDesc(memberId, pageRequest))
                .willReturn(page);

        // when
        final Page<WritingHomeResponse> response = writingService.findAll(memberId, pageRequest);

        //then
        assertThat(response.map(WritingHomeResponse::createdAt).toList()).hasSize(12);
    }

    @Test
    @DisplayName("paging된 글들을 찾을때 writingId가 다를때 에러")
    void findAll_no_match_id() {
        //given
        final Long memberId = 1L;
        final PageRequest pageRequest = PageRequest.of(0, 12);
        final Writing writing = mock(Writing.class);
        final Page<Writing> page = new PageImpl<>(List.of(writing));

        given(writingRepository.findByMemberIdAndWritingStatusOrderByCreatedAtDesc(memberId, pageRequest))
                .willReturn(page);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> writingService.findAll(memberId, pageRequest)
        ).isInstanceOf(WritingNotFoundException.class);
    }
}