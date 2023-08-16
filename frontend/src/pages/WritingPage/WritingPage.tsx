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

  return <WritingViewer categoryId={categoryId} writingId={writingId} />;
};

export default WritingPage;
