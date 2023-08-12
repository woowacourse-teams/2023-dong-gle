import { useMutation } from '@tanstack/react-query';
import { moveToTrash } from 'apis/trash';

export const useDeleteWritings = () => {
  const { mutate } = useMutation(moveToTrash, {
    onSuccess: () => alert('글이 휴지통으로 이동됐습니다.'),
    onError: () => alert('글 삭제가 실패했습니다.'),
  });

  const deleteWritings = (writingIds: number[]) => {
    if (confirm('글을 삭제하시겠습니까?')) mutate(writingIds);
  };

  return deleteWritings;
};
