import { useMutation } from '@tanstack/react-query';
import { OauthPlatforms, getOauthRedirectPlatformURL } from 'constants/components/oauth';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useLocation, useSearchParams } from 'react-router-dom';
import { loginOauth as loginOauthRequest } from 'apis/login';
import { useToast } from 'hooks/@common/useToast';

export const useLoginOauth = () => {
  const { goSpacePage, goIntroducePage } = usePageNavigate();
  const toast = useToast();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const onError = () => {
    toast.show({ type: 'error', message: '로그인을 실패했습니다' });
    goIntroducePage();
  };
  const { mutate: loginOauthMutation } = useMutation(loginOauthRequest, {
    onSuccess: ({ accessToken }) => {
      localStorage.setItem('accessToken', accessToken);
      goSpacePage();
      toast.show({ type: 'success', message: '로그인을 성공했습니다' });
    },
    onError,
  });

  const loginOauth = () => {
    const code = searchParams.get('code');
    const platform = location.pathname.split('/').pop();

    const isOauthPlatform = (platform: string | undefined): platform is OauthPlatforms => {
      return platform ? platform in OauthPlatforms : false;
    };

    if (!isOauthPlatform(platform) || !code) {
      onError();
      return;
    }

    loginOauthMutation({
      platform,
      body: {
        code,
        redirect_uri: getOauthRedirectPlatformURL(platform),
      },
    });
  };

  return { loginOauth };
};
