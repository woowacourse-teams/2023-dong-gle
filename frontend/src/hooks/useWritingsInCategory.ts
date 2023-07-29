import { GetCategoryDetailsResponse } from 'types/apis/category';
import { useGetQuery } from './@common/useGetQuery';
import { getWritingsInCategory } from 'apis/category';
import { useCallback, useEffect, useState } from 'react';

export const useWritingsInCategory = () => {
  const [categoryId, setCategoryId] = useState<number | null>(null);

  const fetcher = useCallback(() => {
    return categoryId !== null
      ? getWritingsInCategory(categoryId)
      : Promise.reject('No Category ID');
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

  return { getWritings, writings: data?.writings };
};
