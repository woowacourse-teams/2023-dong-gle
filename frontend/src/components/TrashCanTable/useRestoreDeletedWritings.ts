import { useMutation, useQueryClient } from '@tanstack/react-query';
import { restoreDeletedWritings as restoreDeletedWritingsAPI } from 'apis/trash';

export const useRestoreDeleteWritings = () => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(restoreDeletedWritingsAPI, {
    onSuccess: () => {
      alert('글이 복구되었습니다.');
      queryClient.invalidateQueries(['deletedWritings']);
    },
    onError: () => alert('글 삭제가 실패했습니다.'),
  });

  const restoreDeletedWritings = (writingIds: number[]) => {
    if (confirm('글을 복구 하시겠습니까?')) mutate(writingIds);
  };

  return restoreDeletedWritings;
};
