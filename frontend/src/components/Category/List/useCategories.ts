import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';

export const useCategories = () => {
  const { data } = useQuery(['categories'], getCategories);

  return { categories: data ? data.categories : null };
};
