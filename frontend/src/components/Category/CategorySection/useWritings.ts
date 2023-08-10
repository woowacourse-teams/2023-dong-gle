import { GetCategoryDetailResponse } from 'types/apis/category';
import { getWritingsInCategory } from 'apis/category';
import { useQuery } from '@tanstack/react-query';

export const useWritings = (categoryId: number, isOpen: boolean) => {
  const { data } = useQuery<GetCategoryDetailResponse>(
    ['writingsInCategory', categoryId],
    () => getWritingsInCategory(categoryId),
    { enabled: Boolean(isOpen) }, // 첫번째 요청만 disabled
  );

  return { writings: data ? data.writings : null };
};
