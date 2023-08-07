import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';
import { useEffect, useState } from 'react';
import { GetCategoriesResponse, GetCategoryDetailResponse } from 'types/apis/category';
import { CategoryWriting } from 'types/components/category';

export const useCategoryDetails = (
  selectedCategoryId: number | null,
  writings: CategoryWriting[] | null,
) => {
  const { data, refetch: getData } = useQuery(['categories'], getCategories);

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
    const updateCategoryDetails = (selectedCategoryId: number, writings: CategoryWriting[]) => {
      setCategoryDetails((prevDetails: GetCategoryDetailResponse[] | null) => {
        return prevDetails
          ? prevDetails.map((detail) =>
              detail.id === selectedCategoryId ? { ...detail, writings } : { ...detail },
            )
          : null;
      });
    };

    if (selectedCategoryId && writings) updateCategoryDetails(selectedCategoryId, writings);
  }, [writings]);

  return { categoryDetails, getCategories: getData };
};
