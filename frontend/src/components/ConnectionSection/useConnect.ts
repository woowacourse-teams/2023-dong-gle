import { useMutation, useQueryClient } from '@tanstack/react-query';
import { storeMediumInfo, disconnect as disconnectRequest } from 'apis/connections';
import { ConnectionPlatforms, getConnectionPlatformURL } from 'constants/components/myPage';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';

export const useConnect = () => {
  const { goMyPage } = usePageNavigate();
  const queryClient = useQueryClient();
  const toast = useToast();
  const { mutate: requestStoreMediumInfo } = useMutation(storeMediumInfo, {
    onSuccess: () => {
      queryClient.invalidateQueries(['member']);
      goMyPage();
      toast.show({ type: 'success', message: '연결을 성공했습니다' });
    },
    onError: () => {
      toast.show({ type: 'error', message: '연결을 실패했습니다' });
    },
  });
  const { mutate: requestDisconnect } = useMutation(disconnectRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['member']);
      toast.show({ type: 'success', message: '연결을 해제했습니다' });
    },
    onError: () => {
      toast.show({ type: 'error', message: '연결 해제를 실패했습니다' });
    },
  });

  const redirect = (destination: ConnectionPlatforms) => {
    window.location.href = getConnectionPlatformURL(destination);
  };

  const disconnect = (platform: ConnectionPlatforms) => requestDisconnect(platform);

  return { requestStoreMediumInfo, redirect, disconnect };
};
