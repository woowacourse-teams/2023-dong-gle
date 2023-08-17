import { useMutation } from '@tanstack/react-query';
import {
  storeNotionInfo as storeNotionInfoRequest,
  storeTistoryInfo as storeTistoryInfoRequest,
} from 'apis/connections';
import { ConnectionPlatforms, getConnectionPlatformRedirectURL } from 'constants/components/myPage';
import { ConnectionMessage } from 'constants/message';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useLocation, useSearchParams } from 'react-router-dom';

export const useStoreConnectionPlatforms = () => {
  const { goMyPage } = usePageNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const toast = useToast();
  const onSuccess = () => {
    goMyPage();
    toast.show({ type: 'success', message: ConnectionMessage.successConnection });
  };
  const onError = () => {
    goMyPage();
    toast.show({ type: 'error', message: ConnectionMessage.errorConnection });
  };
  const { mutate: storeTistoryInfoMutation } = useMutation(storeTistoryInfoRequest, {
    onSuccess,
    onError,
  });
  const { mutate: storeNotionInfoMutation } = useMutation(storeNotionInfoRequest, {
    onSuccess,
    onError,
  });

  const storeInfo = () => {
    const platform = location.pathname.split('/').pop();
    const code = searchParams.get('code');

    const isConnectionPlatforms = (
      platform: string | undefined,
    ): platform is ConnectionPlatforms => {
      return platform ? platform in ConnectionPlatforms : false;
    };

    if (!isConnectionPlatforms(platform) || !code) {
      goMyPage();
      return;
    }

    const store = {
      tistory: () =>
        storeTistoryInfoMutation({
          code,
          redirect_uri: getConnectionPlatformRedirectURL(platform),
        }),
      notion: () =>
        storeNotionInfoMutation({
          code,
          redirect_uri: getConnectionPlatformRedirectURL(platform),
        }),
      medium: () => goMyPage(),
    };

    store[platform]();
  };

  return storeInfo;
};
