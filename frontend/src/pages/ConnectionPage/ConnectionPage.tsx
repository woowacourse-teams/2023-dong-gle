import Spinner from 'components/@common/Spinner/Spinner';
import { ConnectionPlatforms, getConnectionPlatformRedirectURL } from 'constants/components/myPage';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { useStoreConnectionPlatforms } from './useStoreConnectionPlatforms';

const ConnectionPage = () => {
  const { goMyPage } = usePageNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const platform = location.pathname.split('/').pop();
  const mutate = useStoreConnectionPlatforms(platform);

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
