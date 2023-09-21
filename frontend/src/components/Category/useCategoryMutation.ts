import { useMutation, useQueryClient } from '@tanstack/react-query';
import {
  addCategory as addCategoryRequest,
  updateCategoryTitle as updateCategoryTitleRequest,
  updateCategoryOrder as updateCategoryOrderRequest,
  deleteCategory as deleteCategoryRequest,
} from 'apis/category';
import { useToast } from 'hooks/@common/useToast';
import { getErrorMessage } from 'utils/error';

export const useCategoryMutation = (onCategoryAdded?: () => void) => {
  const queryClient = useQueryClient();
  const toast = useToast();

  const { mutate: addCategory } = useMutation(addCategoryRequest, {
    onSuccess: async () => {
      await queryClient.invalidateQueries(['categories']);

      // 카테고리를 가져온 직후가 아닌 렌더링까지 완료 된 후 실행되기 위함.
      setTimeout(() => {
        onCategoryAdded?.();
      });
    },
    onError: (error) => {
      toast.show({ type: 'error', message: getErrorMessage(error) });
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
