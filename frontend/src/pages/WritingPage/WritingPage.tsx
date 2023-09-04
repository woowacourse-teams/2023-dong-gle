import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useGlobalState, useGlobalStateValue } from '@yogjin/react-global-state-hook';
import { activeCategoryIdState, activeWritingInfoState } from 'globalState';

const WritingPage = () => {
  const [activeWritingInfo, setActiveWritingInfo] = useGlobalState(activeWritingInfoState);
  const writingId = activeWritingInfo?.id;
  const isDeleted = activeWritingInfo?.isDeleted;
  const categoryId = useGlobalStateValue(activeCategoryIdState);

  if (!writingId) return <div>존재하지 않는 글입니다.</div>;
  if (!categoryId) return <div>존재하지 않는 카테고리입니다.</div>;

  useEffect(() => {
    return () => setActiveWritingInfo(null);
  }, []);

  return (
    <WritingViewer categoryId={categoryId} writingId={writingId} isDeletedWriting={isDeleted} />
  );
};

export default WritingPage;
