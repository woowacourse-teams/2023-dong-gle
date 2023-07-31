import { addCategory as addCategoryRequest } from 'apis/category';
import useMutation from 'hooks/@common/useMutation';

export const useAddCategory = () => {
  const { mutateQuery } = useMutation({
    fetcher: addCategoryRequest,
  });

  const addCategory = (categoryName: string) => {
    mutateQuery({ categoryName });
  };

  return { addCategory };
};
