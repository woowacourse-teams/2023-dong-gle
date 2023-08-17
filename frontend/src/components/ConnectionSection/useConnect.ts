import { useMutation, useQueryClient } from '@tanstack/react-query';
import { storeMediumInfo, disconnect as disconnectRequest } from 'apis/connections';
import { ConnectionPlatforms, getConnectionPlatformURL } from 'constants/components/myPage';
import { ConnectionMessage } from 'constants/message';
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
      toast.show({ type: 'success', message: ConnectionMessage.successConnection });
    },
    onError: () => {
      toast.show({ type: 'error', message: ConnectionMessage.errorConnection });
    },
  });
  const { mutate: requestDisconnect } = useMutation(disconnectRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['member']);
      toast.show({ type: 'success', message: ConnectionMessage.successDisconnection });
    },
    onError: () => {
      toast.show({ type: 'error', message: ConnectionMessage.errorDisconnection });
    },
  });

  const redirect = (destination: ConnectionPlatforms) => {
    window.location.href = getConnectionPlatformURL(destination);
  };

  const disconnect = (platform: ConnectionPlatforms) => requestDisconnect(platform);

  return { requestStoreMediumInfo, redirect, disconnect };
};
