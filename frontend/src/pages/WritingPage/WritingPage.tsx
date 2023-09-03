import { useLocation, useParams } from 'react-router-dom';

import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useGlobalStateValue, useSetGlobalState } from '@yogjin/react-global-state-hook';
import { activeWritingInfoState } from 'globalState';

const WritingPage = () => {
  const activeWritingInfo = useGlobalStateValue(activeWritingInfoState);
  const writingId = activeWritingInfo?.id;
  const categoryId = Number(useParams()['categoryId']);
  const setActiveWritingInfo = useSetGlobalState(activeWritingInfoState);
  const location = useLocation();
  const isDeletedWriting = location.state.isDeletedWriting;

  useEffect(() => {
    const clearActiveWritingId = () => {
      setActiveWritingInfo?.(null);
    };
    setActiveWritingInfo?.(writingId ? { id: writingId, isDeleted: isDeletedWriting } : null);
    return () => clearActiveWritingId();
  }, [isDeletedWriting]);

  if (!writingId) return <div>존재하지 않는 글입니다.</div>;

  return (
    <WritingViewer
      categoryId={categoryId}
      writingId={writingId}
      isDeletedWriting={isDeletedWriting}
    />
  );
};

export default WritingPage;
