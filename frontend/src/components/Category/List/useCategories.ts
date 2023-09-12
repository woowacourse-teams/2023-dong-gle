import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';
import { useEffect } from 'react';

export const useCategories = () => {
  const { data } = useQuery(['categories'], getCategories);

  useEffect(() => {
    if (!data) return;

    localStorage.setItem('defaultCategoryId', String(data.categories[0].id));
  }, [data]);

  return { categories: data ? data.categories : null };
};
