import { Outlet, Navigate } from 'react-router-dom';
import { useToast } from 'hooks/@common/useToast';
import { useLayoutEffect } from 'react';
import { useAuthToken } from 'hooks/useAuthToken';
import { PATH } from 'constants/path';

const PrivateRouter = () => {
  const { authToken } = useAuthToken();
  const toast = useToast();

  useLayoutEffect(() => {
    if (!authToken) {
      toast.show({ type: 'error', message: '로그인이 필요한 기능입니다.' });
    }
  }, []);

  return authToken ? <Outlet /> : <Navigate to={`${PATH.introducePage}`} />;
};

export default PrivateRouter;
