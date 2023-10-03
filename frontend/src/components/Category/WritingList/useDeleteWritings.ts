import { usePageNavigate } from 'hooks/usePageNavigate';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { moveToTrash } from 'apis/trash';
import { useToast } from 'hooks/@common/useToast';
import { HttpError } from 'utils/apis/HttpError';

export const useDeleteWritings = () => {
  const queryClient = useQueryClient();
  const { goTrashCanPage } = usePageNavigate();
  const toast = useToast();
  const { mutate } = useMutation(moveToTrash, {
    onSuccess: () => {
      toast.show({ type: 'success', message: '글이 휴지통으로 이동됐습니다.' });
      queryClient.invalidateQueries(['deletedWritings']);
      queryClient.invalidateQueries(['writingsInCategory']);
      goTrashCanPage();
    },
    onError: (error) => {
      if (error instanceof HttpError) toast.show({ type: 'error', message: error.message });
    },
  });

  const deleteWritings = (writingIds: number[]) => {
    if (confirm('글을 삭제하시겠습니까?')) mutate(writingIds);
  };

  return deleteWritings;
};
