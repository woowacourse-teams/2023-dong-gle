import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useSetGlobalState } from '@yogjin/react-global-state';
import { activeCategoryIdState, activeWritingInfoState } from 'globalState';
import { useLocation } from 'react-router-dom';

const WritingPage = () => {
  const setActiveWritingInfo = useSetGlobalState(activeWritingInfoState);
  const setActiveCategoryId = useSetGlobalState(activeCategoryIdState);
  const {
    state: { categoryId, writingId, isDeletedWriting },
  } = useLocation();

  useEffect(() => {
    setActiveWritingInfo({ id: writingId, isDeleted: isDeletedWriting });
    setActiveCategoryId(categoryId);
    return () => {
      setActiveWritingInfo(null);
      setActiveCategoryId(Number(localStorage.getItem('defaultCategoryId')));
    };
  }, [categoryId, writingId, isDeletedWriting]);

  if (!writingId) return <div>존재하지 않는 글입니다.</div>;
  if (!categoryId) return <div>존재하지 않는 카테고리입니다.</div>;

  return (
    <WritingViewer
      categoryId={categoryId}
      writingId={writingId}
      isDeletedWriting={isDeletedWriting}
    />
  );
};

export default WritingPage;
