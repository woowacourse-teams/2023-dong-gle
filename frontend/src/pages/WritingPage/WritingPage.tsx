import { useLocation, useParams } from 'react-router-dom';

import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useSetGlobalState } from '@yogjin/react-global-state-hook';
import { activeWritingInfoState } from 'globalState';

const WritingPage = () => {
  const writingId = Number(useParams()['writingId']);
  const categoryId = Number(useParams()['categoryId']);
  const setActiveWritingInfo = useSetGlobalState(activeWritingInfoState);
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
