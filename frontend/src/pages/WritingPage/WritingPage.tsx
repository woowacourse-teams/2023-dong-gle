import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import WritingViewer from 'components/WritingViewer/WritingViewer';
import { usePageContext } from 'pages/Layout/Layout';
import { useEffect } from 'react';

const WritingPage = () => {
  const writingId = Number(useParams()['writingId']);
  const categoryId = Number(useParams()['categoryId']);
  const { setActiveWritingId } = usePageContext();

  useEffect(() => {
    const clearActiveWritingId = () => {
      setActiveWritingId?.(null);
    };
    setActiveWritingId?.(writingId);
    return () => clearActiveWritingId();
  }, []);

  return (
    <S.Article>
      <WritingViewer categoryId={categoryId} writingId={writingId} />
    </S.Article>
  );
};

export default WritingPage;

const S = {
  Article: styled.article`
    width: 90%;

    background-color: ${({ theme }) => theme.color.gray1};
  `,
};
