import { KakaoLoginIcon } from 'assets/icons';
import { getRedirectURL } from 'constants/apis/oauth';
import { getOauthURL } from 'constants/apis/url';
// import { useQuery } from '@tanstack/react-query';
// import { getRedirection } from 'apis/login';

// 추후 oauth 플랫폼이 많아지면 해당 컴포넌트를 추상화해도 될 것같음.
const Kakao = () => {
  // const { refetch } = useQuery(['RedirectionKakao'], () => getRedirection('kakao'), {
  //   enabled: false,
  // });
  // const redirect = () => refetch();

  const redirectToKakao = () => {
    window.location.href = `${getOauthURL('kakao')}?redirect_uri=${getRedirectURL('kakao')}`;
  };

  return (
    <button onClick={redirectToKakao} aria-label='카카오 로그인 화면으로 이동'>
      <KakaoLoginIcon aria-label='카카오 로그인 아이콘' />
    </button>
  );
};

export default Kakao;
