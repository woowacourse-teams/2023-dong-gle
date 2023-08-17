import { KakaoLoginIcon } from 'assets/icons';
import { OauthPlatforms, getOauthURL, getRedirectURL } from 'constants/components/oauth';

const KakaoLoginButton = () => {
  const redirectToKakao = () => {
    window.location.href = `${getOauthURL(OauthPlatforms.kakao)}?redirect_uri=${getRedirectURL(
      OauthPlatforms.kakao,
    )}`;
  };

  return (
    <button onClick={redirectToKakao} aria-label='카카오 로그인 화면으로 이동'>
      <KakaoLoginIcon aria-label='카카오 로그인 아이콘' />
    </button>
  );
};

export default KakaoLoginButton;
