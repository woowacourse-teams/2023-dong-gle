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
      <S.KakaoLoginDesktopText>카카오로 시작하기</S.KakaoLoginDesktopText>
      <S.KakaoLoginMobileText>로그인</S.KakaoLoginMobileText>
    </S.KakaoLoginButton>
  );
};

export default KakaoLoginButton;

const KakaoLoginText = styled.p`
  flex: 1;
  font-size: 1.6rem;
`;

const S = {
  KakaoLoginButton: styled.button`
    display: flex;
    width: 320px;
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

    @media (max-width: 768px) {
      width: 100%;
    }
  `,

  KakaoLoginDesktopText: styled(KakaoLoginText)`
    @media (max-width: 480px) {
      display: none;
    }
  `,

  KakaoLoginMobileText: styled(KakaoLoginText)`
    display: none;

    @media (max-width: 480px) {
      display: block;
    }
  `,
};
