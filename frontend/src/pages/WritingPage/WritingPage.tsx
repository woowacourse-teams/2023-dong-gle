import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useSetGlobalState } from '@yogjin/react-global-state';
import { activeCategoryIdState, activeWritingInfoState } from 'globalState';
import { useLocation } from 'react-router-dom';
import use활성화된카테고리설정 from 'hooks/use활성화된카테고리설정';
import use활성화된글설정 from 'hooks/use활성화된글설정';

const WritingPage = () => {
  const {
    state: { categoryId, writingId, isDeletedWriting },
  } = useLocation();
  const activeWritingInfo = { id: writingId, isDeleted: isDeletedWriting };

  use활성화된카테고리설정(categoryId);
  use활성화된글설정(activeWritingInfo);

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
