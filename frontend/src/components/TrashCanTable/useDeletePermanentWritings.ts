import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deletePermanentWritings as deletePermanentWritingsAPI } from 'apis/trash';
import { useToast } from 'hooks/@common/useToast';
import { HttpError } from 'utils/apis/HttpError';

export const useDeletePermanentWritings = () => {
  const queryClient = useQueryClient();
  const toast = useToast();
  const { mutate } = useMutation(deletePermanentWritingsAPI, {
    onSuccess: () => {
      toast.show({ type: 'success', message: '글이 삭제되었습니다.' });
      queryClient.invalidateQueries(['deletedWritings']);
    },
    onError: (error) => {
      if (error instanceof HttpError) toast.show({ type: 'error', message: error.message });
    },
  });

  const deletePermanentWritings = (writingIds: number[]) => {
    if (writingIds.length === 0) {
      alert('삭제할 글을 선택해주세요.');
      return;
    }

    if (confirm('글을 영구 삭제하시겠습니까?')) mutate(writingIds);
  };

  return deletePermanentWritings;
};
