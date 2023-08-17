import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';

export const useTokenError = () => {
  const { goIntroducePage } = usePageNavigate();
  const toast = useToast();

  const handleTokenError = () => {
    localStorage.removeItem('accessToken');

    goIntroducePage();

    toast.show({ type: 'error', message: '다시 로그인해 주세요.' });
  };

  return { handleTokenError };
};
