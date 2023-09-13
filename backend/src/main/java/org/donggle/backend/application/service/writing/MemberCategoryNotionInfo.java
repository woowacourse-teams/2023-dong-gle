package org.donggle.backend.application.service.writing;

import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.member.Member;

public record MemberCategoryNotionInfo(
        Member member,
        Category category,
        String notionToken
) {
}
