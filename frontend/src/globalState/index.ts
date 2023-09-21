import { globalState } from '@yogjin/react-global-state';

export type ActiveWritingInfo = {
  id: number;
  isDeleted: boolean;
};

export const activeWritingInfoState = globalState<ActiveWritingInfo | null>(null);

const initialActiveCategoryId = -1;
export const activeCategoryIdState = globalState<number>(initialActiveCategoryId);
