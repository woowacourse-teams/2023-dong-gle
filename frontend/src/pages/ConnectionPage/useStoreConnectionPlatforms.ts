import { useMutation } from '@tanstack/react-query';
import { storeNotionInfo, storeTistoryInfo } from 'apis/connections';
import { ConnectionPlatforms } from 'constants/components/myPage';
import { ConnectionMessage } from 'constants/message';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';

export const useStoreConnectionPlatforms = (platform: string | undefined) => {
  const { goMyPage } = usePageNavigate();
  const toast = useToast();
  const onSuccess = () => {
    goMyPage();
    toast.show({ type: 'success', message: ConnectionMessage.successConnection });
  };
  const onError = () => {
    goMyPage();
    toast.show({ type: 'error', message: ConnectionMessage.errorConnection });
  };
  const { mutate: storeTistoryInfoMutation } = useMutation(storeTistoryInfo, {
    onSuccess,
    onError,
  });
  const { mutate: storeNotionInfoMutation } = useMutation(storeNotionInfo, {
    onSuccess,
    onError,
  });

  switch (platform) {
    case ConnectionPlatforms.tistory:
      return storeTistoryInfoMutation;
    case ConnectionPlatforms.notion:
      return storeNotionInfoMutation;
  }
};
