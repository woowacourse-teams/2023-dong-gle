import { useQuery } from '@tanstack/react-query';
import { getTistoryCategories } from 'apis/blogs';
import { useToast } from 'hooks/@common/useToast';
import { GetTistoryCategoriesResponse } from 'types/apis/blogs';

export const useTistoryCategories = () => {
  const toast = useToast();
  const { data, isLoading } = useQuery<GetTistoryCategoriesResponse>(
    ['tistoryCategories'],
    getTistoryCategories,
    {
      onError: () =>
        toast.show({ type: 'error', message: '티스토리 카테고리 목록을 불러오지 못했습니다.' }),
    },
  );

  return { categories: data?.categories, isLoading };
};
