import { useMutation } from '@tanstack/react-query';
import {
  storeTistoryInfo as storeTistoryInfoRequest,
  storeNotionInfo as storeNotionInfoRequest,
} from 'apis/connections';
import Spinner from 'components/@common/Spinner/Spinner';
import { ConnectionPlatforms, getConnectionPlatformRedirectURL } from 'constants/components/myPage';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { styled } from 'styled-components';

export const useStoreConnectionPlatforms = (platform: string | undefined) => {
  const { goMyPage } = usePageNavigate();
  const { mutate: storeTistoryInfo } = useMutation(storeTistoryInfoRequest, {
    onSuccess: goMyPage,
  });
  const { mutate: storeNotionInfo } = useMutation(storeNotionInfoRequest, {
    onSuccess: goMyPage,
  });

  switch (platform) {
    case ConnectionPlatforms.tistory:
      return storeTistoryInfo;
    case ConnectionPlatforms.notion:
      return storeNotionInfo;
  }
};

const ConnectionPage = () => {
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

    if (!isConnectionPlatforms(platform) || !mutate || !code) {
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
      <p>연결 중입니다 ...</p>
    </S.Section>
  );
};

export default ConnectionPage;

const S = {
  Section: styled.section`
    display: flex;
    justify-content: center;
    align-items: center;
  `,
};
