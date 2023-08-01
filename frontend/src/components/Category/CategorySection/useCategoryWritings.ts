import { GetCategoryDetailResponse } from 'types/apis/category';
import { useGetQuery } from '../../../hooks/@common/useGetQuery';
import { getWritingsInCategory } from 'apis/category';
import { useEffect, useState } from 'react';

export const useCategoryWritings = () => {
  const [categoryId, setCategoryId] = useState<number | null>(null);

  const { data, getData } = useGetQuery<GetCategoryDetailResponse>({
    fetcher: () => {
      return categoryId ? getWritingsInCategory(categoryId) : Promise.reject('No Category ID');
    },
  });

  useEffect(() => {
    getData();
  }, [categoryId]);

  const getWritings = (categoryId: number) => {
    setCategoryId(categoryId);
  };

  return {
    getWritings,
    categoryId: data ? data.id : null,
    writings: data ? data.writings : null,
  };
};
