import { GetCategoriesResponse } from 'types/apis/category';
import { useGetQuery } from './@common/useGetQuery';
import { getCategories } from 'apis/category';
import Category from 'components/Category/Category';
import { usePatchCategory } from './usePatchCategory';
import { useEraseCategory } from './useEraseCategory';
import { ReactNode } from 'react';

export const useGenerateCategoryTuple = () => {
  const { data, getData } = useGetQuery<GetCategoriesResponse>({
    fetcher: getCategories,
  });

  const { renameCategory, moveCategory } = usePatchCategory();

  const { eraseCategory } = useEraseCategory();

  const generateCategoryTuple = (writings: ReactNode = null) => {
    if (!data) return null;

    return data.categories.map((category) => {
      return [
        <Category
          id={category.id}
          categoryName={category.categoryName}
          onTrashcanClick={() => eraseCategory(category.id)}
        />,
        writings,
      ];
    });
  };

  return generateCategoryTuple;
};
