import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import PublishingSection from 'components/PublishingSection/PublishingSection';
import WritingViewer from 'components/WritingViewer/WritingViewer';
import { LAYOUT_STYLE, SIDEBAR_STYLE, sidebarStyle } from 'styles/layoutStyle';

const WritingPage = () => {
  const { writingId } = useParams();

  // TODO: getWritingProperties() 실행

  return (
    <S.Container>
      <S.Article>
        <WritingViewer writingId={Number(writingId)} />
      </S.Article>
      <S.SidebarSection>
        <PublishingSection writingId={Number(writingId)} isPublished={false} />
      </S.SidebarSection>
      {/** 사이드바 컴포넌트 완성되면 대체 */}
    </S.Container>
  );
};

export default WritingPage;

const S = {
  Container: styled.div`
    display: flex;
    gap: ${LAYOUT_STYLE.gap};
    height: 100%;
  `,

  Article: styled.article`
    flex: 1;
    max-width: calc(
      100vw - (${SIDEBAR_STYLE.width} + ${LAYOUT_STYLE.padding} + ${LAYOUT_STYLE.gap}) * 2
    );
    border: ${LAYOUT_STYLE.border};
    border-radius: 8px;

    background-color: ${({ theme }) => theme.color.gray1};
  `,

  SidebarSection: styled.section`
    ${sidebarStyle}
  `,
};
