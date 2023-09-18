import { globalState } from '@yogjin/react-global-state';

type ActiveWritingInfo = {
  id: number;
  isDeleted: boolean;
};

export const activeWritingInfoState = globalState<ActiveWritingInfo | null>(null);

export const activeCategoryIdState = globalState<number | null>(
  Number(localStorage.getItem('defaultCategoryId')),
);
