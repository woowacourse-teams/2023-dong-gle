import { useGlobalState } from '@yogjin/react-global-state-hook';
import { activeWritingInfoState } from 'globalState';

const useActiveWritingInfo = () => {
  const [activeWritingInfo, setActiveWritingInfo] = useGlobalState(activeWritingInfoState);

  if (!activeWritingInfo) {
    throw new Error('activeWritingInfo가 null 입니다.');
  }

  return [activeWritingInfo, setActiveWritingInfo] as const;
};

export default useActiveWritingInfo;
