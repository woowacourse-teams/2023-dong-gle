package org.donggle.backend.fix;

import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;

import java.util.ArrayList;
import java.util.List;

import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.domain.writing.WritingStatus.DELETED;
import static org.donggle.backend.domain.writing.WritingStatus.TRASHED;
import static org.donggle.backend.fix.CategoryFixture.basicCategory;
import static org.donggle.backend.fix.MemberFixture.beaver;

public class WritingFixture {

    public static final Writing writing_ACTIVE = new Writing(1L, beaver, new Title("Title 1"), basicCategory, List.of(new NormalBlock(Depth.empty(), BlockType.PARAGRAPH, RawText.from("안녕"), List.of())), null, ACTIVE);
    public static final Writing writing_DELETED = new Writing(2L, beaver, new Title("Title 1"), basicCategory, new ArrayList<>(), null, DELETED);

    public static List<Writing> createWritings_ACTIVE() {
        final Title title1 = new Title("Title 1");
        final Title title2 = new Title("Title 2");

        final Writing writing2 = new Writing(2L, beaver, title2, basicCategory, new ArrayList<>(), null, ACTIVE);
        final Writing writing1 = new Writing(1L, beaver, title1, basicCategory, new ArrayList<>(), writing2, ACTIVE);

        final List<Writing> writings = new ArrayList<>();
        writings.add(writing1);
        writings.add(writing2);

        return writings;
    }

    public static List<Writing> createPageWritings_12() {
        final Title title1 = new Title("Title 1");
        final Title title2 = new Title("Title 2");
        final Title title3 = new Title("Title 3");
        final Title title4 = new Title("Title 4");
        final Title title5 = new Title("Title 5");
        final Title title6 = new Title("Title 6");
        final Title title7 = new Title("Title 7");
        final Title title8 = new Title("Title 8");
        final Title title9 = new Title("Title 9");
        final Title title10 = new Title("Title 10");
        final Title title11 = new Title("Title 11");
        final Title title12 = new Title("Title 12");
        final Writing writing12 = new Writing(12L, beaver, title12, basicCategory, new ArrayList<>(), null, ACTIVE);
        final Writing writing11 = new Writing(11L, beaver, title11, basicCategory, new ArrayList<>(), writing12, ACTIVE);
        final Writing writing10 = new Writing(10L, beaver, title10, basicCategory, new ArrayList<>(), writing11, ACTIVE);
        final Writing writing9 = new Writing(9L, beaver, title9, basicCategory, new ArrayList<>(), writing10, ACTIVE);
        final Writing writing8 = new Writing(8L, beaver, title8, basicCategory, new ArrayList<>(), writing9, ACTIVE);
        final Writing writing7 = new Writing(7L, beaver, title7, basicCategory, new ArrayList<>(), writing8, ACTIVE);
        final Writing writing6 = new Writing(5L, beaver, title6, basicCategory, new ArrayList<>(), writing7, ACTIVE);
        final Writing writing5 = new Writing(5L, beaver, title5, basicCategory, new ArrayList<>(), writing6, ACTIVE);
        final Writing writing4 = new Writing(4L, beaver, title4, basicCategory, new ArrayList<>(), writing5, ACTIVE);
        final Writing writing3 = new Writing(3L, beaver, title3, basicCategory, new ArrayList<>(), writing4, ACTIVE);
        final Writing writing2 = new Writing(2L, beaver, title2, basicCategory, new ArrayList<>(), writing3, ACTIVE);
        final Writing writing1 = new Writing(1L, beaver, title1, basicCategory, new ArrayList<>(), writing2, ACTIVE);

        return List.of(writing1, writing2, writing3, writing4, writing5, writing6, writing7, writing8, writing9, writing10, writing11, writing12);
    }

    public static List<Writing> createWritings_TRASHED() {
        final Title title1 = new Title("Title 1");
        final Title title2 = new Title("Title 2");

        final Writing writing2 = new Writing(2L, beaver, title2, basicCategory, new ArrayList<>(), null, TRASHED);
        final Writing writing1 = new Writing(1L, beaver, title1, basicCategory, new ArrayList<>(), writing2, TRASHED);

        final List<Writing> writings = new ArrayList<>();
        writings.add(writing1);
        writings.add(writing2);

        return writings;
    }
}
