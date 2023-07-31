import { patchCategory } from 'apis/category';
import useMutation from '../../../hooks/@common/useMutation';

export const usePatchCategory = () => {
  const { mutateQuery } = useMutation({
    fetcher: patchCategory,
  });

  const renameCategory = async (categoryId: number, newCategoryName: string) => {
    const body = {
      categoryName: newCategoryName,
    };

    await mutateQuery({ categoryId, body });
  };

  const moveCategory = async (categoryId: number, nextCategoryId: number) => {
    const body = {
      nextCategoryId,
    };

    await mutateQuery({ categoryId, body });
  };

  return { renameCategory, moveCategory };
};
