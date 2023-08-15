import { useQuery } from '@tanstack/react-query';
import { getDeletedWritings } from 'apis/trash';
import { GetDeletedWritingsResponse } from 'types/apis/trash';

export const useDeletedWritings = () => {
  const { data, isLoading } = useQuery<GetDeletedWritingsResponse>(
    ['deletedWritings'],
    getDeletedWritings,
    {
      onError: () => alert('휴지통의 글을 불러올 수 없습니다'),
    },
  );

  return { deletedWritings: data ? data.writings : null, isLoading };
};
