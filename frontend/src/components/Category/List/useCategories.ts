import { useSetGlobalState } from '@yogjin/react-global-state-hook';
import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';
import { useEffect } from 'react';
import { activeCategoryIdState } from 'globalState';

export const useCategories = () => {
  const { data } = useQuery(['categories'], getCategories);
  const setActiveCategoryIdState = useSetGlobalState(activeCategoryIdState);

  useEffect(() => {
    if (!data) return;

    localStorage.setItem('defaultCategoryId', String(data.categories[0].id));
    setActiveCategoryIdState(data.categories[0].id);
  }, [data]);

  return { categories: data ? data.categories : null };
};
