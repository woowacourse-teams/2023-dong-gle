import { getCategories } from 'apis/category';
import { useGetQuery } from 'hooks/@common/useGetQuery';
import { useEffect, useState } from 'react';
import { GetCategoriesResponse, GetCategoryDetailResponse } from 'types/apis/category';

export type Writing = {
  id: number;
  title: string;
};

export const useCategoryDetails = (categoryId: number | null, writings: Writing[] | null) => {
  const { data } = useGetQuery<GetCategoriesResponse>({
    fetcher: getCategories,
  });

  const [categoryDetails, setCategoryDetails] = useState<GetCategoryDetailResponse[] | null>(null);

  useEffect(() => {
    setCategoryDetails(() => {
      return data
        ? data.categories.map((category) => {
            return {
              id: category.id,
              categoryName: category.categoryName,
              writings: null,
            };
          })
        : null;
    });
  }, [data]);

  useEffect(() => {
    const updateCategoryDetails = (categoryId: number, writings: Writing[]) => {
      setCategoryDetails((prevDetails: GetCategoryDetailResponse[] | null) => {
        return prevDetails
          ? prevDetails.map((detail) =>
              detail.id === categoryId ? { ...detail, writings } : { ...detail },
            )
          : null;
      });
    };

    if (writings && categoryId) {
      updateCategoryDetails(categoryId, writings);
    }
  }, [writings]);

  return { categoryDetails };
};
