import { useMutation, useQueryClient } from '@tanstack/react-query';
import { updateWritingOrder } from 'apis/writings';

export const useWritingOrderUpdate = () => {
  const queryClient = useQueryClient();

  return useMutation(updateWritingOrder, {
    onSuccess: () => {
      queryClient.invalidateQueries(['detailWritings']);
      queryClient.invalidateQueries(['writingsInCategory']);
    },
  });
};
