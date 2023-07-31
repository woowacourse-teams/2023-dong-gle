import { GetCategoryDetailResponse } from 'types/apis/category';
import { useGetQuery } from '../../../hooks/@common/useGetQuery';
import { getWritingsInCategory } from 'apis/category';
import { useCallback, useEffect, useState } from 'react';

export const useCategoryWritings = () => {
  const [categoryId, setCategoryId] = useState<number | null>(null);

  const fetcher = useCallback(() => {
    return categoryId ? getWritingsInCategory(categoryId) : Promise.reject('No Category ID');
  }, [categoryId]);

  const { data, getData } = useGetQuery<GetCategoryDetailResponse>({
    fetcher,
  });

  useEffect(() => {
    getData();
  }, [categoryId]);

  const getWritingsRequest = (categoryId: number) => {
    setCategoryId(categoryId);
  };

  return {
    getWritingsRequest,
    categoryId: data ? data.id : null,
    writings: data ? data.writings : null,
  };
};
