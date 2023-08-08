import { useMutation, useQueryClient } from '@tanstack/react-query';
import {
  addCategory as addCategoryRequest,
  patchCategory as patchCategoryRequest,
  deleteCategory as deleteCategoryRequest,
} from 'apis/category';

export const useCategoryMutation = () => {
  const queryClient = useQueryClient();

  const { mutate: addCategory } = useMutation(addCategoryRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
    },
  });

  const { mutate: patchCategory } = useMutation(patchCategoryRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
      queryClient.invalidateQueries(['detailWritings']);
    },
  });

  const { mutate: deleteCategory } = useMutation(deleteCategoryRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
    },
  });

  return { addCategory, patchCategory, deleteCategory };
};
