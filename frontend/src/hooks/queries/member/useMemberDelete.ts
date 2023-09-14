import { useMutation } from '@tanstack/react-query';
import { deleteMemberAccount } from 'apis/member';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { getErrorMessage } from 'utils/error';

export const useMemberDelete = () => {
  const toast = useToast();
  const { goIntroducePage } = usePageNavigate();

  return useMutation(deleteMemberAccount, {
    onSuccess: () => {
      localStorage.removeItem('accessToken');
      goIntroducePage();
      toast.show({ type: 'success', message: '탈퇴가 완료되었습니다.' });
    },
    onError: (error) => {
      toast.show({ type: 'error', message: getErrorMessage(error) });
    },
  });
};
