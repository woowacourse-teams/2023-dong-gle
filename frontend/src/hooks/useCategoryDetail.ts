import { GetCategoryDetailsResponse } from 'types/apis/category';
import { useGetQuery } from './@common/useGetQuery';
import { getWritingsInCategory } from 'apis/category';
import { useCallback, useEffect, useState } from 'react';

export const useCategoryDetail = () => {
  const [categoryId, setCategoryId] = useState<number | null>(null);

  const fetcher = useCallback(() => {
    return categoryId ? getWritingsInCategory(categoryId) : Promise.reject('No Category ID');
  }, [categoryId]);

  const { data, getData } = useGetQuery<GetCategoryDetailsResponse>({
    fetcher,
  });

  useEffect(() => {
    getData();
  }, [categoryId]);

  const getWritings = (categoryId: number) => {
    setCategoryId(categoryId);
  };

  return { getWritings, categoryId: data?.id, writings: data?.writings };
};
