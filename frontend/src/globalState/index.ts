import { globalState } from '@yogjin/react-global-state-hook';

type ActiveWritingInfo = {
  id: number;
  isDeleted: boolean;
};

export const activeWritingInfoState = globalState<ActiveWritingInfo | null>(null);

export const activeCategoryIdState = globalState<number | null>(null);
