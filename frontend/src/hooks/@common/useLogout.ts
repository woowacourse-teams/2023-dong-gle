import { useMutation } from '@tanstack/react-query';
import { useToast } from './useToast';
import { logout as logoutAPI } from 'apis/login';
import { usePageNavigate } from 'hooks/usePageNavigate';

export const useLogout = () => {
  const toast = useToast();
  const { goIntroducePage } = usePageNavigate();
  const logout = useMutation(logoutAPI, {
    onSuccess: () => {
      localStorage.removeItem('accessToken');
      goIntroducePage();
      toast.show({ type: 'success', message: '로그아웃이 완료되었습니다.' });
    },
    onError: () => {
      toast.show({ type: 'error', message: '로그아웃에 실패했습니다.' });
    },
  });

  return logout;
};
