import Spinner from 'components/@common/Spinner/Spinner';
import { useEffect } from 'react';
import { styled } from 'styled-components';
import { useLoginOauth } from './useLoginOauth';

const OauthPage = () => {
  const { loginOauth } = useLoginOauth();

  useEffect(loginOauth, []);

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
