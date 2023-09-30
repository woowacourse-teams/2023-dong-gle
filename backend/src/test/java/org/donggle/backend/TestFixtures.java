package org.donggle.backend;

import org.donggle.backend.domain.auth.RefreshToken;
import org.donggle.backend.domain.category.Category;
import org.donggle.backend.domain.category.CategoryName;
import org.donggle.backend.domain.member.Member;
import org.donggle.backend.domain.member.MemberCredentials;
import org.donggle.backend.domain.member.MemberName;
import org.donggle.backend.domain.oauth.SocialType;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Style;
import org.donggle.backend.domain.writing.StyleRange;
import org.donggle.backend.domain.writing.StyleType;
import org.donggle.backend.domain.writing.Title;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.CodeBlock;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.HorizontalRulesBlock;
import org.donggle.backend.domain.writing.block.ImageBlock;
import org.donggle.backend.domain.writing.block.ImageCaption;
import org.donggle.backend.domain.writing.block.ImageUrl;
import org.donggle.backend.domain.writing.block.Language;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;

import java.util.List;

public class TestFixtures {
    public static final Member MEMBER = Member.of(
            new MemberName("테스트 멤버"),
            1234L,
            SocialType.KAKAO
    );

    public static final MemberCredentials MEMBER_CREDENTIALS = MemberCredentials.basic(MEMBER);

    public static final RefreshToken REFRESH_TOKEN = new RefreshToken("testRefreshToken", MEMBER);

    public static final Category BASIC_CATEGORY = Category.of(new CategoryName("기본"), MEMBER);
    public static final Category ANOTHER_CATEGORY = Category.of(new CategoryName("추가"), MEMBER);

    public static final Writing WRITING_WITH_NORMAL_BLOCK = Writing.of(MEMBER, new Title("WRITING_WITH_NORMAL_BLOCK"),
            BASIC_CATEGORY,
            List.of(
                    new NormalBlock(
                            Depth.from(1),
                            BlockType.PARAGRAPH,
                            RawText.from("테스트 글입니다"),
                            List.of(
                                    new Style(new StyleRange(0, 2), StyleType.BOLD),
                                    new Style(new StyleRange(3, 4), StyleType.ITALIC)
                            )
                    )
            )
    );

    public static final Writing GENERAL_WRITING1 = Writing.of(MEMBER, new Title("GENERAL_WRITING1"),
            BASIC_CATEGORY,
            List.of(
                    new NormalBlock(
                            Depth.from(1),
                            BlockType.PARAGRAPH,
                            RawText.from("테스트 글입니다"),
                            List.of(
                                    new Style(new StyleRange(0, 2), StyleType.BOLD),
                                    new Style(new StyleRange(3, 4), StyleType.ITALIC)
                            )
                    ),
                    new CodeBlock(
                            Depth.from(1),
                            BlockType.CODE_BLOCK,
                            RawText.from("public static void main(String... args) {}"),
                            Language.from("java")
                    ),
                    new ImageBlock(
                            Depth.from(1),
                            BlockType.IMAGE,
                            new ImageUrl("localhost:8080/hello-world.png"),
                            new ImageCaption("테스트 이미지")
                    ),
                    new HorizontalRulesBlock(
                            Depth.from(1),
                            BlockType.HORIZONTAL_RULES,
                            RawText.from("")
                    )
            )
    );

    public static final Writing GENERAL_WRITING2 = Writing.of(MEMBER, new Title("GENERAL_WRITING2"),
            BASIC_CATEGORY,
            List.of(
                    new NormalBlock(
                            Depth.from(1),
                            BlockType.PARAGRAPH,
                            RawText.from("테스트 글입니다"),
                            List.of(
                                    new Style(new StyleRange(0, 2), StyleType.BOLD),
                                    new Style(new StyleRange(3, 4), StyleType.ITALIC)
                            )
                    ),
                    new CodeBlock(
                            Depth.from(1),
                            BlockType.CODE_BLOCK,
                            RawText.from("public static void main(String... args) {}"),
                            Language.from("java")
                    ),
                    new ImageBlock(
                            Depth.from(1),
                            BlockType.IMAGE,
                            new ImageUrl("localhost:8080/hello-world.png"),
                            new ImageCaption("테스트 이미지")
                    ),
                    new HorizontalRulesBlock(
                            Depth.from(1),
                            BlockType.HORIZONTAL_RULES,
                            RawText.from("")
                    )
            )
    );
}
