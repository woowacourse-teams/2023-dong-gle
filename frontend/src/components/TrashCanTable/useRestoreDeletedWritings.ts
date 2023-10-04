import { useMutation, useQueryClient } from '@tanstack/react-query';
import { restoreDeletedWritings as restoreDeletedWritingsAPI } from 'apis/trash';
import { useToast } from 'hooks/@common/useToast';
import { HttpError } from 'utils/apis/HttpError';

export const useRestoreDeleteWritings = (onSuccessCbFn: () => void) => {
  const queryClient = useQueryClient();
  const toast = useToast();
  const { mutate } = useMutation(restoreDeletedWritingsAPI, {
    onSuccess: () => {
      toast.show({ type: 'success', message: '글이 복구되었습니다.' });
      onSuccessCbFn();
      queryClient.invalidateQueries(['deletedWritings']);
    },
    onError: (error) => {
      if (error instanceof HttpError) toast.show({ type: 'error', message: error.message });
    },
  });

  const restoreDeletedWritings = (writingIds: number[]) => {
    if (writingIds.length === 0) {
      alert('복구할 글을 선택해주세요.');
      return;
    }

    if (confirm('글을 복구 하시겠습니까?')) mutate(writingIds);
  };

  return restoreDeletedWritings;
};
