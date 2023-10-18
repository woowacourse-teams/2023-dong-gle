import { KakaoSymbol } from 'assets/icons';
import { OauthPlatforms, getOauthPlatformURL } from 'constants/components/oauth';
import { MAX_WIDTH } from 'constants/style';
import { css, styled } from 'styled-components';

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

const generateResponsiveStyle = {
  kakaoLoginButton: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        width: 100%;
      }
    `;
  },

  kakaoLoginDesktopText: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        display: none;
      }
    `;
  },

  kakaoLoginMobileText: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        display: block;
      }
    `;
  },
};

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

    ${() => generateResponsiveStyle.kakaoLoginButton()}
  `,

  KakaoLoginDesktopText: styled(KakaoLoginText)`
    ${() => generateResponsiveStyle.kakaoLoginDesktopText()}
  `,

  KakaoLoginMobileText: styled(KakaoLoginText)`
    display: none;

    ${() => generateResponsiveStyle.kakaoLoginMobileText()}
  `,
};
