import { GetCategoriesResponse } from 'types/apis/category';
import { useGetQuery } from './@common/useGetQuery';
import { getCategories } from 'apis/category';

export const useCategories = () => {
  const { data } = useGetQuery<GetCategoriesResponse>({
    fetcher: getCategories,
  });

  return { categories: data?.categories };
};
