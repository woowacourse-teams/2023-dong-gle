package org.donggle.backend.fix;

import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;

import java.util.ArrayList;
import java.util.List;

import static org.donggle.backend.domain.writing.WritingStatus.ACTIVE;
import static org.donggle.backend.fix.CategoryFixture.basicCategory;
import static org.donggle.backend.fix.MemberFixture.beaver;

public class WritingFixture {
    public static List<Writing> createWritings() {
        final Title title1 = new Title("Title 1");
        final Title title2 = new Title("Title 2");

        final Writing writing2 = new Writing(2L, beaver, title2, basicCategory, new ArrayList<>(), null, ACTIVE);
        final Writing writing1 = new Writing(1L, beaver, title1, basicCategory, new ArrayList<>(), writing2, ACTIVE);

        final List<Writing> writings = new ArrayList<>();
        writings.add(writing1);
        writings.add(writing2);

        return writings;
    }
}
