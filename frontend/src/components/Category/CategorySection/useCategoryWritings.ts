import { GetCategoryDetailResponse } from 'types/apis/category';
import { getWritingsInCategory } from 'apis/category';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';

export const useCategoryWritings = () => {
  const [selectedCategoryId, setSelectedCategoryId] = useState<number | null>(null);

  const { data } = useQuery<GetCategoryDetailResponse>(
    ['categories', selectedCategoryId, 'writings'],
    () => getWritingsInCategory(selectedCategoryId!),
    { enabled: Boolean(selectedCategoryId) }, // 첫번째 요청만 disabled
  );

  return { writings: data ? data.writings : null, selectedCategoryId, setSelectedCategoryId };
};
