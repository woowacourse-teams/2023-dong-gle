import { KakaoLoginIcon } from 'assets/icons';
import { getRedirectURL } from 'constants/apis/oauth';
import { getOauthURL } from 'constants/apis/url';

const KakaoLoginButton = () => {
  const redirectToKakao = () => {
    window.location.href = `${getOauthURL('kakao')}?redirect_uri=${getRedirectURL('kakao')}`;
  };

  return (
    <button onClick={redirectToKakao} aria-label='카카오 로그인 화면으로 이동'>
      <KakaoLoginIcon aria-label='카카오 로그인 아이콘' />
    </button>
  );
};

export default KakaoLoginButton;
