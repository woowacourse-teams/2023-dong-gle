import { useLocation, useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import WritingViewer from 'components/WritingViewer/WritingViewer';
import { usePageContext } from 'pages/Layout/Layout';
import { useEffect } from 'react';

const WritingPage = () => {
  const writingId = Number(useParams()['writingId']);
  const categoryId = Number(useParams()['categoryId']);
  const { setActiveWritingInfo } = usePageContext();
  const location = useLocation();
  const isDeletedWriting = location.state.isDeletedWriting;

  useEffect(() => {
    const clearActiveWritingId = () => {
      setActiveWritingInfo?.(null);
    };
    setActiveWritingInfo?.({ id: writingId, isDeleted: isDeletedWriting });
    return () => clearActiveWritingId();
  }, [isDeletedWriting]);

  return (
    <WritingViewer
      categoryId={categoryId}
      writingId={writingId}
      isDeletedWriting={isDeletedWriting}
    />
  );
};

export default WritingPage;
