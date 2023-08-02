import { getCategories } from 'apis/category';
import { useGetQuery } from 'hooks/@common/useGetQuery';
import { useEffect, useState } from 'react';
import { GetCategoriesResponse, GetCategoryDetailResponse } from 'types/apis/category';
import { Writing } from 'types/components/category';

export const useCategoryDetails = (categoryId: number | null, writings: Writing[] | null) => {
  const { data, getData } = useGetQuery<GetCategoriesResponse>({
    fetcher: getCategories,
  });

  const [categoryDetails, setCategoryDetails] = useState<GetCategoryDetailResponse[] | null>(null);

  useEffect(() => {
    if (!data) return;

    const initCategoryDetails = () => {
      return data.categories.map((category) => ({
        id: category.id,
        categoryName: category.categoryName,
        writings: null,
      }));
    };

    const updateAddedCategory = (prevDetails: GetCategoryDetailResponse[]) => {
      return data.categories.map((category) => {
        const prevDetail = prevDetails.find((detail) => detail.id === category.id);
        const addedCategory = {
          id: category.id,
          categoryName: category.categoryName,
          writings: null,
        };

        return prevDetail ? prevDetail : addedCategory;
      });
    };

    setCategoryDetails((prevDetails) => {
      return prevDetails ? updateAddedCategory(prevDetails) : initCategoryDetails();
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

    if (categoryId && writings) updateCategoryDetails(categoryId, writings);
  }, [writings]);

  return { categoryDetails, getCategories: getData };
};
