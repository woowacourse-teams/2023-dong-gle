package org.donggle.backend.application.service;

import org.donggle.backend.application.repository.CategoryRepository;
import org.donggle.backend.application.repository.MemberRepository;
import org.donggle.backend.application.repository.WritingRepository;
import org.donggle.backend.application.service.request.WritingModifyRequest;
import org.donggle.backend.application.service.writing.WritingFacadeService;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class ModifyWritingOrderTest {
    @Autowired
    private WritingFacadeService writingService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WritingRepository writingRepository;

    private Member member;
    private Category basicCategory;
    private Category anotherCategory;
    private List<Writing> basicWritings = new ArrayList<>();
    private List<Writing> anotherWritings = new ArrayList<>();

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Member.of(
                new MemberName("테스트 멤버"),
                1234L,
                SocialType.KAKAO
        ));
        basicCategory = categoryRepository.save(Category.of(new CategoryName("기본"), member));
        anotherCategory = categoryRepository.save(Category.of(new CategoryName("추가"), member));

        Writing target1 = null;
        for (int i = 1; i <= 4; i++) {
            final Writing basicWriting = writingRepository.save(
                    Writing.of(member, new Title("기본 글" + i),
                            basicCategory,
                            List.of(new NormalBlock(
                                    Depth.from(1),
                                    BlockType.PARAGRAPH,
                                    RawText.from("테스트 글입니다."),
                                    List.of(new Style(new StyleRange(0, 2), StyleType.BOLD)))
                            )
                    )
            );
            basicWritings.add(basicWriting);
            if (i == 1) {
                target1 = basicWriting;
            } else {
                target1.changeNextWriting(basicWriting);
                target1 = basicWriting;
            }
        }

        Writing target2 = null;
        for (int i = 1; i <= 4; i++) {
            final Writing anotherWriting = writingRepository.save(
                    Writing.of(member, new Title("추가 글" + i),
                            anotherCategory,
                            List.of(new NormalBlock(
                                    Depth.from(1),
                                    BlockType.PARAGRAPH,
                                    RawText.from("테스트 글입니다."),
                                    List.of(new Style(new StyleRange(0, 2), StyleType.BOLD)))
                            )
                    )
            );
            anotherWritings.add(anotherWriting);
            if (i == 1) {
                target2 = anotherWriting;
            } else {
                target2.changeNextWriting(anotherWriting);
                target2 = anotherWriting;
            }
        }
    }

    @Nested
    @DisplayName("하나의 카테고리 내에서 움직이는 경우")
    class MovingInOneCategoryTest {
        @Test
        @DisplayName("[기본]['1', 2, 3, 4] -> [기본][2, '1', 3, 4]")
        void movingInOneCategory1() {
            // given
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(0).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), basicWritings.get(2).getId())
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            //when
            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Test
        @DisplayName("[기본]['1', 2, 3, 4] -> [기본][2, 3, 4, '1']")
        void movingInOneCategory2() {
            //when
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(0).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), -1L)
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            //when
            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Test
        @DisplayName("[기본][1, '2', 3, 4] -> [기본][1, 3, '2', 4]")
        void movingInOneCategory3() {
            //when
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(1).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), basicWritings.get(3).getId())
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            //when
            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Test
        @DisplayName("[기본][1, '2', 3, 4] -> [기본][1, 3, 4, '2']")
        void movingInOneCategory4() {
            //when
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(1).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), -1L)
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Test
        @DisplayName("[기본][1, '2', 3, 4] -> [기본]['2', 1, 3, 4]")
        void movingInOneCategory5() {
            //when
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(1).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), basicWritings.get(0).getId())
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Test
        @DisplayName("[기본][1, 2, 3, '4'] -> [기본][1, '4', 2, 3]")
        void movingInOneCategory6() {
            //when
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(3).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), basicWritings.get(1).getId())
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Test
        @DisplayName("[기본][1, 2, 3, '4'] -> [기본]['4', 1, 2, 3]")
        void movingInOneCategory7() {
            //when
            writingService.modifyWritingOrder(
                    member.getId(),
                    basicWritings.get(3).getId(),
                    new WritingModifyRequest(null, basicCategory.getId(), basicWritings.get(0).getId())
            );

            final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
            assertThat(writings).hasSize(4);
            final List<Writing> nextWritings = writings.stream()
                    .map(Writing::getNextWriting)
                    .toList();

            writings.removeAll(nextWritings);

            //then
            final Writing firstWriting = writings.get(0);
            assertAll(
                    () -> assertThat(firstWriting).isEqualTo(basicWritings.get(3)),
                    () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(0)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(1)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                    () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
            );
        }

        @Nested
        @DisplayName("예외적인 요청에 대한 처리")
        class ExceptionalMovingTest {
            /**
             * 원래의 순서 그대로 요청이 들어올 경우, early return하고 예외 없이 순서를 유지한다.
             */
            @Test
            @DisplayName("[기본][1, '2', 3, 4] -> [기본][1, '2', 3, 4]")
            void exceptionalMoving1() {
                //when
                writingService.modifyWritingOrder(
                        member.getId(),
                        basicWritings.get(1).getId(),
                        new WritingModifyRequest(null, basicCategory.getId(),
                                basicWritings.get(2).getId())
                );

                final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
                assertThat(writings).hasSize(4);
                final List<Writing> nextWritings = writings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                writings.removeAll(nextWritings);

                //then
                final Writing firstWriting = writings.get(0);
                assertAll(
                        () -> assertThat(firstWriting).isEqualTo(basicWritings.get(0)),
                        () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(1)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
                );
            }

            /**
             * 원래의 순서 그대로 요청이 들어올 경우, early return하고 예외 없이 순서를 유지한다. (이미 마지막인데, 마지막으로 이동 요청이 올 경우)
             */
            @Test
            @DisplayName("[기본][1, 2, 3, '4'] -> [기본][1, 2, 3, '4']")
            void exceptionalMoving2() {
                //when
                writingService.modifyWritingOrder(
                        member.getId(),
                        basicWritings.get(3).getId(),
                        new WritingModifyRequest(null, basicCategory.getId(), -1L)
                );

                final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
                assertThat(writings).hasSize(4);
                final List<Writing> nextWritings = writings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                writings.removeAll(nextWritings);

                //then
                final Writing firstWriting = writings.get(0);
                assertAll(
                        () -> assertThat(firstWriting).isEqualTo(basicWritings.get(0)),
                        () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(1)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
                );
            }

            /**
             * 요청이 자기 자신을 가리키도록 한다면, 이를 무시하고 early return한다. 또한 예외 없이 순서를 유지한다.
             */
            @Test
            @DisplayName("[기본][1, '2', 3, 4] -> [기본][1, '2', 3, 4]")
            void modifyWritingOrder9() {
                //when
                writingService.modifyWritingOrder(
                        member.getId(),
                        basicWritings.get(1).getId(),
                        new WritingModifyRequest(null, basicCategory.getId(), basicWritings.get(1).getId())
                );

                final List<Writing> writings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
                assertThat(writings).hasSize(4);
                final List<Writing> nextWritings = writings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                writings.removeAll(nextWritings);

                //then
                final Writing firstWriting = writings.get(0);
                assertAll(
                        () -> assertThat(firstWriting).isEqualTo(basicWritings.get(0)),
                        () -> assertThat(firstWriting.getNextWriting()).isEqualTo(basicWritings.get(1)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                        () -> assertThat(firstWriting.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
                );
            }
        }
    }


    @Nested
    @DisplayName("두 개의 카테고리에서 움직이는 경우")
    class movingInTwoCategoriesTest {

        @Nested
        @DisplayName("비어 있는 카테고리로 글을 이동시키는 경우")
        class MoveWritingIntoEmptyCategory {
            private Category emptyCategory;

            @BeforeEach
            void setUp() {
                emptyCategory = categoryRepository.save(Category.of(new CategoryName("empty"), member));
                anotherCategory.changeNextCategory(emptyCategory);
            }

            @Test
            @DisplayName("[추가]['5', 6, 7, 8]/[empty][ ] -> [추가][6, 7, 8]/[empty]['5']")
            void moveIntoEmptyCategory1() {
                //given
                writingService.modifyWritingOrder(
                        member.getId(),
                        anotherWritings.get(0).getId(),
                        new WritingModifyRequest(null, emptyCategory.getId(), -1L)
                );

                //when
                final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
                assertThat(anotherCategoryWritings).hasSize(3);
                final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                final List<Writing> emptyCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(emptyCategory.getId(), ACTIVE);
                assertThat(emptyCategoryWritings).hasSize(1);
                final List<Writing> emptyNextWritings = emptyCategoryWritings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                anotherCategoryWritings.removeAll(anotherNextWritings);
                emptyCategoryWritings.removeAll(emptyNextWritings);

                //then
                final Writing another1Writing = anotherCategoryWritings.get(0);
                final Writing empty1Writing = emptyCategoryWritings.get(0);
                assertAll(
                        () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(1)),
                        () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(2)),
                        () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                        () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                        () -> assertThat(empty1Writing).isEqualTo(anotherWritings.get(0)),
                        () -> assertThat(empty1Writing.getNextWriting()).isNull()
                );
            }

            @Test
            @DisplayName("[추가][5, '6', 7, 8]/[empty][ ] -> [추가][5, 7, 8]/[empty]['6']")
            void moveIntoEmptyCategory2() {
                //given
                writingService.modifyWritingOrder(
                        member.getId(),
                        anotherWritings.get(1).getId(),
                        new WritingModifyRequest(null, emptyCategory.getId(), -1L)
                );

                //when
                final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
                assertThat(anotherCategoryWritings).hasSize(3);
                final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                final List<Writing> emptyCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(emptyCategory.getId(), ACTIVE);
                assertThat(emptyCategoryWritings).hasSize(1);
                final List<Writing> emptyNextWritings = emptyCategoryWritings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                anotherCategoryWritings.removeAll(anotherNextWritings);
                emptyCategoryWritings.removeAll(emptyNextWritings);

                //then
                final Writing another1Writing = anotherCategoryWritings.get(0);
                final Writing empty1Writing = emptyCategoryWritings.get(0);
                assertAll(
                        () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                        () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(2)),
                        () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                        () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                        () -> assertThat(empty1Writing).isEqualTo(anotherWritings.get(1)),
                        () -> assertThat(empty1Writing.getNextWriting()).isNull()
                );
            }

            @Test
            @DisplayName("[추가][5, 6, 7, '8']/[empty][ ] -> [추가][5, 6, 7]/[empty]['8']")
            void moveIntoEmptyCategory3() {
                //given
                writingService.modifyWritingOrder(
                        member.getId(),
                        anotherWritings.get(3).getId(),
                        new WritingModifyRequest(null, emptyCategory.getId(), -1L)
                );

                //when
                final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
                assertThat(anotherCategoryWritings).hasSize(3);
                final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                final List<Writing> emptyCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(emptyCategory.getId(), ACTIVE);
                assertThat(emptyCategoryWritings).hasSize(1);
                final List<Writing> emptyNextWritings = emptyCategoryWritings.stream()
                        .map(Writing::getNextWriting)
                        .toList();

                anotherCategoryWritings.removeAll(anotherNextWritings);
                emptyCategoryWritings.removeAll(emptyNextWritings);

                //then
                final Writing another1Writing = anotherCategoryWritings.get(0);
                final Writing empty1Writing = emptyCategoryWritings.get(0);
                assertAll(
                        () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                        () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(1)),
                        () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                        () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                        () -> assertThat(empty1Writing).isEqualTo(anotherWritings.get(3)),
                        () -> assertThat(empty1Writing.getNextWriting()).isNull()
                );
            }
        }
    }

    @Test
    @DisplayName("[기본]['1', 2, 3, 4]/[추가][5, 6, 7, 8] -> [기본][2, 3, 4]/[추가]['1', 5, 6, 7, 8]")
    void movingInTwoCategories1() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(0).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), anotherWritings.get(0).getId())
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(1)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본]['1', 2, 3, 4]/[추가][5, 6, 7, 8] -> [기본][2, 3, 4]/[추가][5, '1', 6, 7, 8]")
    void movingInTwoCategories2() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(0).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), anotherWritings.get(1).getId())
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(1)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(basicWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본]['1', 2, 3, 4]/[추가][5, 6, 7, 8] -> [기본][2, 3, 4]/[추가][5, 6, 7, 8, '1']")
    void movingInTwoCategories3() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(0).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), -1L)
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(1)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본][1, '2', 3, 4]/[추가][5, 6, 7, 8] -> [기본][1, 3, 4]/[추가]['2', 5, 6, 7, 8]")
    void movingInTwoCategories4() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(1).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), anotherWritings.get(0).getId())
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(basicWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본][1, '2', 3, 4]/[추가][5, 6, 7, 8] -> [기본][1, 3, 4]/[추가][5, 6, '2', 7, 8]")
    void movingInTwoCategories5() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(1).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), anotherWritings.get(2).getId())
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본][1, '2', 3, 4]/[추가][5, 6, 7, 8] -> [기본][1, 3, 4]/[추가][5, 6, 7, 8, '2']")
    void movingInTwoCategories6() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(1).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), -1L)
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본][1, 2, 3, '4']/[추가][5, 6, 7, 8] -> [기본][1, 2, 3]/[추가]['4', 5, 6, 7, 8]")
    void movingInTwoCategories7() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(3).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), anotherWritings.get(0).getId())
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(1)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(basicWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본][1, 2, 3, '4']/[추가][5, 6, 7, 8] -> [기본][1, 2, 3]/[추가][5, 6, '4', 7, 8]")
    void movingInTwoCategories8() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(3).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), anotherWritings.get(2).getId())
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(1)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }

    @Test
    @DisplayName("[기본][1, 2, 3, '4']/[추가][5, 6, 7, 8] -> [기본][1, 2, 3]/[추가][5, 6, 7, 8, '4']")
    void movingInTwoCategories9() {
        //when
        writingService.modifyWritingOrder(
                member.getId(),
                basicWritings.get(3).getId(),
                new WritingModifyRequest(null, anotherCategory.getId(), -1L)
        );

        final List<Writing> basicCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(basicCategory.getId(), ACTIVE);
        assertThat(basicCategoryWritings).hasSize(3);
        final List<Writing> basicNextWritings = basicCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        final List<Writing> anotherCategoryWritings = writingRepository.findAllByCategoryIdAndStatus(anotherCategory.getId(), ACTIVE);
        assertThat(anotherCategoryWritings).hasSize(5);
        final List<Writing> anotherNextWritings = anotherCategoryWritings.stream()
                .map(Writing::getNextWriting)
                .toList();

        basicCategoryWritings.removeAll(basicNextWritings);
        anotherCategoryWritings.removeAll(anotherNextWritings);

        //then
        final Writing basic1Writing = basicCategoryWritings.get(0);
        final Writing another1Writing = anotherCategoryWritings.get(0);
        assertAll(
                () -> assertThat(basic1Writing).isEqualTo(basicWritings.get(0)),
                () -> assertThat(basic1Writing.getNextWriting()).isEqualTo(basicWritings.get(1)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(2)),
                () -> assertThat(basic1Writing.getNextWriting().getNextWriting().getNextWriting()).isNull(),

                () -> assertThat(another1Writing).isEqualTo(anotherWritings.get(0)),
                () -> assertThat(another1Writing.getNextWriting()).isEqualTo(anotherWritings.get(1)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(2)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting()).isEqualTo(anotherWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isEqualTo(basicWritings.get(3)),
                () -> assertThat(another1Writing.getNextWriting().getNextWriting().getNextWriting().getNextWriting().getNextWriting()).isNull()
        );
    }
}
