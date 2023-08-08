import { GetCategoryDetailResponse } from 'types/apis/category';
import { getWritingsInCategory } from 'apis/category';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';

export const useCategoryWritings = () => {
  const [selectedCategoryId, setSelectedCategoryId] = useState<number | null>(null);

  const { data } = useQuery<GetCategoryDetailResponse>(
    ['writingsInCategory', selectedCategoryId],
    () => getWritingsInCategory(selectedCategoryId!),
    { enabled: Boolean(selectedCategoryId) }, // 첫번째 요청만 disabled
  );

  return {
    selectedCategoryWritings: data ? data.writings : null,
    selectedCategoryId,
    setSelectedCategoryId,
  };
};
