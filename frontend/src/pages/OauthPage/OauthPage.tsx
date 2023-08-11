import { useMutation } from '@tanstack/react-query';
import { postOauthLogin } from 'apis/login';
import { Platforms, getRedirectURL } from 'constants/apis/oauth';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { OauthPlatform } from 'types/apis/login';

const OauthPage = () => {
  const { goHomePage } = usePageNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const { mutate } = useMutation(postOauthLogin, {
    onSuccess: goHomePage,
    onError: (error) => alert(error),
  });

  useEffect(() => {
    const platform = location.pathname.split('/').pop();

    const isOauthPlatform = (platform: string | undefined): platform is OauthPlatform => {
      return platform ? platform in Platforms : false;
    };

    if (!isOauthPlatform(platform)) return;

    mutate({
      platform,
      body: {
        code: searchParams.get('code') ?? '',
        redirect_uri: getRedirectURL(platform),
      },
    });
  }, []);

  return <>Oauth 빈페이지</>;
};

export default OauthPage;
