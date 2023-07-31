import { useEffect, useState } from 'react';
import { useCategories } from './useCategories';
import { GetCategoryDetailResponse, Writing } from 'types/apis/category';

export const useCategoryDetails = () => {
  const { categories } = useCategories();
  const [categoryDetails, setCategoryDetails] = useState<GetCategoryDetailResponse[] | null>(null);

  useEffect(() => {
    setCategoryDetails(() => {
      return categories
        ? categories.map((category) => {
            return {
              id: category.id,
              categoryName: category.categoryName,
              writings: null,
            };
          })
        : null;
    });
  }, [categories]);

  const updateWritings = (categoryId: number, writings: Writing[]) => {
    setCategoryDetails((prevDetails: GetCategoryDetailResponse[] | null) => {
      return prevDetails
        ? prevDetails.map((detail) =>
            detail.id === categoryId ? { ...detail, writings } : { ...detail },
          )
        : null;
    });
  };

  return { categoryDetails, updateWritings };
};
