import { styled } from 'styled-components';
import PublishingSection from 'components/PublishingSection/PublishingSection';
import WritingViewer from 'components/WritingViewer/WritingViewer';
import { LAYOUT_COMMON_STYLE, SIDEBAR_SECTION_STYLE } from 'pages/Layout/Layout';

const WritingPage = () => {
  return (
    <S.Container>
      <S.Article>
        <WritingViewer writingId={200} />
      </S.Article>
      <S.SidebarSection>
        <PublishingSection writingId={1} isPublished={false} />
      </S.SidebarSection>
      {/** 사이드바 컴포넌트 완성되면 대체 */}
    </S.Container>
  );
};

export default WritingPage;

const S = {
  Container: styled.div`
    display: flex;
    gap: ${LAYOUT_COMMON_STYLE.gap};
    height: 100%;
  `,

  Article: styled.article`
    flex: 1;
    border: ${LAYOUT_COMMON_STYLE.border};
    border-radius: 8px;

    background-color: ${({ theme }) => theme.color.gray1};
  `,

  SidebarSection: styled.section`
    ${SIDEBAR_SECTION_STYLE}
  `,
};
