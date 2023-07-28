import { deleteCategory } from 'apis/category';
import useMutation from './@common/useMutation';

export const useEraseCategory = () => {
  const { mutateQuery } = useMutation({
    fetcher: deleteCategory,
  });

  const eraseCategory = async (categoryId: number) => {
    await mutateQuery(categoryId);
  };

  return { eraseCategory };
};
