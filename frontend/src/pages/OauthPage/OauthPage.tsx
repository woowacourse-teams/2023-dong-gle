import { useMutation } from '@tanstack/react-query';
import { postOauthLogin } from 'apis/login';
import { Platforms, getRedirectURL } from 'constants/apis/oauth';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { OauthPlatform } from 'types/apis/login';

const OauthPage = () => {
  const { goHomePage } = usePageNavigate();
  const onError = () => {
    alert('에러: 로그인을 실패했습니다.');
    goHomePage();
  };
  const { mutate } = useMutation(postOauthLogin, {
    onSuccess: goHomePage,
    onError,
  });
  const location = useLocation();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const code = searchParams.get('code');
    const platform = location.pathname.split('/').pop();

    const isOauthPlatform = (platform: string | undefined): platform is OauthPlatform => {
      return platform ? platform in Platforms : false;
    };

    if (!isOauthPlatform(platform) || !code) {
      onError();
      return;
    }

    mutate({
      platform,
      body: {
        code,
        redirect_uri: getRedirectURL(platform),
      },
    });
  }, []);

  return <>Oauth 빈페이지</>;
};

export default OauthPage;
