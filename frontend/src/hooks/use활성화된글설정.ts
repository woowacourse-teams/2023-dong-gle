import { useSetGlobalState } from '@yogjin/react-global-state';
import { ActiveWritingInfo, activeCategoryIdState, activeWritingInfoState } from 'globalState';
import { useEffect } from 'react';

const use활성화된글설정 = (activeWritingInfo: ActiveWritingInfo) => {
  const setActiveWritingInfo = useSetGlobalState(activeWritingInfoState);

  useEffect(() => {
    setActiveWritingInfo(activeWritingInfo);
    return () => {
      setActiveWritingInfo(null);
    };
  }, [activeWritingInfo]);
};

export default use활성화된글설정;
