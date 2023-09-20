import { globalState } from '@yogjin/react-global-state';

type ActiveWritingInfo = {
  id: number;
  isDeleted: boolean;
};

export const activeWritingInfoState = globalState<ActiveWritingInfo | null>(null);

const initialActiveCategoryId = -1;
export const activeCategoryIdState = globalState<number>(initialActiveCategoryId);
