import useMutation from 'hooks/@common/useMutation';
import {
  addCategory as addCategoryRequest,
  patchCategory as patchCategoryRequest,
  deleteCategory as deleteCategoryRequest,
} from 'apis/category';

export const useCategoryMutation = () => {
  const { mutateQuery: addCategory } = useMutation({
    fetcher: addCategoryRequest,
  });

  const { mutateQuery: patchCategory } = useMutation({
    fetcher: patchCategoryRequest,
  });

  const { mutateQuery: deleteCategory } = useMutation({
    fetcher: deleteCategoryRequest,
  });

  return { addCategory, patchCategory, deleteCategory };
};
