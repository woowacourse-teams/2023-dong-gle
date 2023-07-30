import { useParams } from 'react-router-dom';
import { RuleSet, css, styled } from 'styled-components';
import PublishingSection from 'components/PublishingSection/PublishingSection';
import WritingViewer from 'components/WritingViewer/WritingViewer';
import { LAYOUT_STYLE, SIDEBAR_STYLE, genMainPageWidth, sidebarStyle } from 'styles/layoutStyle';
import { PageContextType, usePageContext } from 'pages/Layout/Layout';

const WritingPage = () => {
  const { writingId } = useParams();
  const { isLeftSidebarOpen, isRightSidebarOpen } = usePageContext();

  // TODO: getWritingProperties() 실행

  return (
    <>
      <S.Article isLeftSidebarOpen={isLeftSidebarOpen} isRightSidebarOpen={isRightSidebarOpen}>
        <WritingViewer writingId={Number(writingId)} />
      </S.Article>
      <S.SidebarSection isRightSidebarOpen={isRightSidebarOpen}>
        <PublishingSection writingId={Number(writingId)} isPublished={false} />
      </S.SidebarSection>
      {/** 사이드바 컴포넌트 완성되면 대체 */}
    </>
  );
};

export default WritingPage;

const S = {
  Article: styled.article<PageContextType>`
    width: 90%;

    background-color: ${({ theme }) => theme.color.gray1};
  `,

  SidebarSection: styled.section<Pick<PageContextType, 'isRightSidebarOpen'>>`
    ${sidebarStyle}
    display: ${({ isRightSidebarOpen }) => !isRightSidebarOpen && 'none'};
  `,
};
