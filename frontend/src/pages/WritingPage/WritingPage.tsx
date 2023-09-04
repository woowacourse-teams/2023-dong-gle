import { useLocation, useParams } from 'react-router-dom';

import WritingViewer from 'components/WritingViewer/WritingViewer';
import { useEffect } from 'react';
import { useGlobalStateValue, useSetGlobalState } from '@yogjin/react-global-state-hook';
import { activeWritingInfoState } from 'globalState';

const WritingPage = () => {
  const activeWritingInfo = useGlobalStateValue(activeWritingInfoState);
  const writingId = activeWritingInfo?.id;
  const isDeleted = activeWritingInfo?.isDeleted;
  const categoryId = Number(useParams()['categoryId']);

  if (!writingId) return <div>존재하지 않는 글입니다.</div>;

  return (
    <WritingViewer categoryId={categoryId} writingId={writingId} isDeletedWriting={isDeleted} />
  );
};

export default WritingPage;
