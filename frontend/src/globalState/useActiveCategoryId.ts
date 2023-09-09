import { useGlobalState } from '@yogjin/react-global-state-hook';
import { activeCategoryIdState } from 'globalState';

const useActiveCategoryId = () => {
  const [activeCategoryId, setActiveCategoryId] = useGlobalState(activeCategoryIdState);

  if (!activeCategoryId) {
    throw new Error('activeCategoryId가 null 입니다.');
  }

  return [activeCategoryId, setActiveCategoryId] as const;
};

export default useActiveCategoryId;
