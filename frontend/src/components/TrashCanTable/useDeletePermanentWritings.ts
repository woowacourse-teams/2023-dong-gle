import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deletePermanentWritings as deletePermanentWritingsAPI } from 'apis/trash';

export const useDeletePermanentWritings = () => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(deletePermanentWritingsAPI, {
    onSuccess: () => {
      alert('글이 삭제되었습니다.');
      queryClient.invalidateQueries(['deletedWritings']);
    },
    onError: () => alert('글 삭제가 실패했습니다.'),
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
