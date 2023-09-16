import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useSetGlobalState } from '@yogjin/react-global-state-hook';
import { activeCategoryIdState, activeWritingInfoState } from 'globalState';
import { useLocation } from 'react-router-dom';

const WritingPage = () => {
  const setActiveWritingInfo = useSetGlobalState(activeWritingInfoState);
  const setActiveCategoryId = useSetGlobalState(activeCategoryIdState);
  const {
    state: { categoryId, writingId, isDeletedWriting },
  } = useLocation();

  if (!writingId) return <div>존재하지 않는 글입니다.</div>;
  if (!categoryId) return <div>존재하지 않는 카테고리입니다.</div>;

  useEffect(() => {
    setActiveWritingInfo({ id: writingId, isDeleted: isDeletedWriting });
    setActiveCategoryId(categoryId);
    return () => setActiveWritingInfo(null);
  }, [categoryId, writingId, isDeletedWriting]);

  return (
    <WritingViewer
      categoryId={categoryId}
      writingId={writingId}
      isDeletedWriting={isDeletedWriting}
    />
  );
};

export default WritingPage;
