import { useMutation } from '@tanstack/react-query';
import { loginOauth } from 'apis/login';
import { OauthPlatforms, getOauthRedirectPlatformURL } from 'constants/components/oauth';
import Spinner from 'components/@common/Spinner/Spinner';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { styled } from 'styled-components';

const OauthPage = () => {
  const { goIntroducePage, goSpacePage } = usePageNavigate();
  const onError = () => {
    alert('에러: 로그인을 실패했습니다.');
    goIntroducePage();
  };
  const { mutate } = useMutation(loginOauth, {
    onSuccess: ({ accessToken }) => {
      localStorage.setItem('accessToken', JSON.stringify(accessToken));
      goSpacePage();
    },
    onError,
  });
  const location = useLocation();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const code = searchParams.get('code');
    const platform = location.pathname.split('/').pop();

    const isOauthPlatform = (platform: string | undefined): platform is OauthPlatforms => {
      return platform ? platform in OauthPlatforms : false;
    };

    if (!isOauthPlatform(platform) || !code) {
      onError();
      return;
    }

    mutate({
      platform,
      body: {
        code,
        redirect_uri: getOauthRedirectPlatformURL(platform),
      },
    });
  }, []);

  return (
    <S.SpinnerContainer>
      <Spinner size={48} thickness={6} />
      <p>로그인 중입니다...</p>
    </S.SpinnerContainer>
  );
};

export default OauthPage;

const S = {
  SpinnerContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1.5rem;
    width: 100vw;
    height: 100vh;
    font-size: 1.5rem;
  `,
};
