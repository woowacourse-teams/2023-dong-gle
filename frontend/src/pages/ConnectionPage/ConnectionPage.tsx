import { useMutation } from '@tanstack/react-query';
import {
  storeTistoryInfo as storeTistoryInfoRequest,
  storeNotionInfo as storeNotionInfoRequest,
} from 'apis/connections';
import Spinner from 'components/@common/Spinner/Spinner';
import { ConnectionPlatforms, getConnectionPlatformRedirectURL } from 'constants/components/myPage';
import { ConnectionMessage } from 'constants/message';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { styled } from 'styled-components';

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
  const { mutate: storeTistoryInfo } = useMutation(storeTistoryInfoRequest, {
    onSuccess,
    onError,
  });
  const { mutate: storeNotionInfo } = useMutation(storeNotionInfoRequest, {
    onSuccess,
    onError,
  });

  switch (platform) {
    case ConnectionPlatforms.tistory:
      return storeTistoryInfo;
    case ConnectionPlatforms.notion:
      return storeNotionInfo;
  }
};

const ConnectionPage = () => {
  const { goMyPage } = usePageNavigate();
  const location = useLocation();
  const platform = location.pathname.split('/').pop();
  const mutate = useStoreConnectionPlatforms(platform);
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const code = searchParams.get('code');

    const isConnectionPlatforms = (
      platform: string | undefined,
    ): platform is ConnectionPlatforms => {
      return platform ? platform in ConnectionPlatforms : false;
    };

    if (!code || !mutate || !isConnectionPlatforms(platform)) {
      goMyPage();
      return;
    }

    mutate({
      code,
      redirect_uri: getConnectionPlatformRedirectURL(platform),
    });
  }, []);

  return (
    <S.Section>
      <Spinner size={60} thickness={8} />
      <h1>연결 중입니다 ...</h1>
    </S.Section>
  );
};

export default ConnectionPage;

const S = {
  Section: styled.section`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 4rem;
    width: 100vw;
    height: 100vh;
  `,
};
