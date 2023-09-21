import { useSetGlobalState } from '@yogjin/react-global-state';
import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';
import { activeCategoryIdState } from 'globalState';
import { useEffect } from 'react';

export const useCategories = () => {
  const setActiveCategoryIdState = useSetGlobalState(activeCategoryIdState);
  const { data } = useQuery(['categories'], getCategories, {});

  useEffect(() => {
    if (!data) return;
    const defaultCategoryId = data.categories[0].id;

    localStorage.setItem('defaultCategoryId', String(defaultCategoryId));
    setActiveCategoryIdState(defaultCategoryId);
  }, [data]);

  return { categories: data ? data.categories : null };
};
