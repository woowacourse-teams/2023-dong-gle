import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';
import { useEffect, useState } from 'react';
import { GetCategoryDetailResponse } from 'types/apis/category';
import { CategoryWriting } from 'types/components/category';
import { useCategoryWritings } from './useCategoryWritings';

export const useCategoryDetails = () => {
  const { selectedCategoryWritings, selectedCategoryId, setSelectedCategoryId } =
    useCategoryWritings();

  const { data: categories } = useQuery(['categories'], getCategories);

  const [categoryDetails, setCategoryDetails] = useState<GetCategoryDetailResponse[]>([]);

  useEffect(() => {
    if (!categories) return;

    const initCategoryDetails = () => {
      // 동글 첫 진입 시, 카테고리 목록 데이터 만드는 함수
      return categories.categories.map((category) => ({
        id: category.id,
        categoryName: category.categoryName,
        writings: null,
      }));
    };

    setCategoryDetails(initCategoryDetails());
  }, [categories]);

  useEffect(() => {
    const updateCategoryDetails = (selectedCategoryId: number, writings: CategoryWriting[]) => {
      // 카테고리 토글 클릭 시 selectedCategoryWritings 업데이트
      setCategoryDetails(
        (prevDetails) =>
          prevDetails?.map((detail) =>
            detail.id === selectedCategoryId ? { ...detail, writings } : { ...detail },
          ),
      );
    };

    if (selectedCategoryId && selectedCategoryWritings)
      updateCategoryDetails(selectedCategoryId, selectedCategoryWritings);
  }, [selectedCategoryId, selectedCategoryWritings]);

  return { categoryDetails, setSelectedCategoryId };
};
