import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import WritingViewer from 'components/WritingViewer/WritingViewer';
import { PageContext, usePageContext } from 'pages/Layout/Layout';
import { useEffect } from 'react';

const WritingPage = () => {
  const writingId = Number(useParams()['writingId']);
  const { isLeftSidebarOpen, isRightSidebarOpen, setActiveWritingId } = usePageContext();

  useEffect(() => {
    const clearActiveWritingId = () => {
      setActiveWritingId?.(null);
    };
    setActiveWritingId?.(writingId);
    return () => clearActiveWritingId();
  }, []);

  // TODO: getWritingProperties() 실행

  return (
    <S.Article isLeftSidebarOpen={isLeftSidebarOpen} isRightSidebarOpen={isRightSidebarOpen}>
      <WritingViewer writingId={Number(writingId)} />
    </S.Article>
  );
};

export default WritingPage;

const S = {
  Article: styled.article<PageContext>`
    width: 90%;

    background-color: ${({ theme }) => theme.color.gray1};
  `,
};
