import { useMutation, useQueryClient } from '@tanstack/react-query';
import {
  addCategory as addCategoryRequest,
  updateCategoryTitle as updateCategoryTitleRequest,
  updateCategoryOrder as updateCategoryOrderRequest,
  deleteCategory as deleteCategoryRequest,
} from 'apis/category';

export const useCategoryMutation = () => {
  const queryClient = useQueryClient();

  const { mutate: addCategory } = useMutation(addCategoryRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
    },
  });

  const { mutate: updateCategoryTitle } = useMutation(updateCategoryTitleRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
      queryClient.invalidateQueries(['detailWritings']);
    },
  });

  const { mutate: updateCategoryOrder } = useMutation(updateCategoryOrderRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
      queryClient.invalidateQueries(['detailWritings']);
    },
  });

  const { mutate: deleteCategory } = useMutation(deleteCategoryRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
      queryClient.invalidateQueries(['writingsInCategory']);
    },
  });

  return { addCategory, updateCategoryTitle, updateCategoryOrder, deleteCategory };
};
