import { useSetGlobalState } from '@yogjin/react-global-state';
import { activeCategoryIdState } from 'globalState';
import { useEffect } from 'react';

const use활성화된카테고리설정 = (categoryId: number) => {
  const setActiveCategoryId = useSetGlobalState(activeCategoryIdState);

  useEffect(() => {
    setActiveCategoryId(categoryId);
    return () => {
      setActiveCategoryId(Number(localStorage.getItem('defaultCategoryId')));
    };
  }, [categoryId]);
};

export default use활성화된카테고리설정;
