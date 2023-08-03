import { GetCategoryDetailResponse } from 'types/apis/category';
import { useGetQuery } from '../../../hooks/@common/useGetQuery';
import { getWritingsInCategory } from 'apis/category';
import { useEffect } from 'react';

export const useCategoryWritings = (selectedCategoryId: number) => {
  const { data, getData } = useGetQuery<GetCategoryDetailResponse>({
    fetcher: () => getWritingsInCategory(selectedCategoryId),
    enabled: Boolean(selectedCategoryId),
  });

  useEffect(() => {
    const refetch = async () => {
      if (!selectedCategoryId) return;

      await getData();
    };
    refetch();
  }, [selectedCategoryId]);

  return data ? data.writings : null;
};
