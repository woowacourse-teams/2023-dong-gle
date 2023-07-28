import { GetCategoryDetailsResponse } from 'types/apis/category';
import { useGetQuery } from './@common/useGetQuery';
import { getWritingsInCategory } from 'apis/category';

export const useGetWritingsInCategory = (categoryId: number) => {
  const { data, getData } = useGetQuery<GetCategoryDetailsResponse>({
    fetcher: () => getWritingsInCategory(categoryId),
  });

  const getWritings = async () => {
    await getData();
  };

  return { getWritings, data };
};
