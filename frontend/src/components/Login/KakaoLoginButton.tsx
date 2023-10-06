import { KakaoSymbol } from 'assets/icons';
import { OauthPlatforms, getOauthPlatformURL } from 'constants/components/oauth';
import { styled } from 'styled-components';

const KakaoLoginButton = () => {
  const redirectToKakao = () => {
    window.location.href = getOauthPlatformURL(OauthPlatforms.kakao);
  };

  return (
    <S.KakaoLoginButton onClick={redirectToKakao} aria-label='카카오 로그인 화면으로 이동'>
      <KakaoSymbol width='18px' height='18px' />
      <S.KakaoLoginText>카카오로 시작하기</S.KakaoLoginText>
    </S.KakaoLoginButton>
  );
};

export default KakaoLoginButton;

const S = {
  KakaoLoginButton: styled.button`
    display: flex;
    width: 300px;
    height: 45px;
    justify-content: center;
    align-items: center;
    padding: 12px;
    background-color: #fee500;
    border-radius: 6px;
    font-size: 1.4rem;
    font-weight: 600;

    &:hover {
      background-color: #ffe000;
    }
  `,

  KakaoLoginText: styled.span`
    flex: 1;
  `,
};
