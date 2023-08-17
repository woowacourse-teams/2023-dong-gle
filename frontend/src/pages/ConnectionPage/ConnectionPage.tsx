import Spinner from 'components/@common/Spinner/Spinner';
import { useEffect } from 'react';
import { styled } from 'styled-components';
import { useStoreConnectionPlatforms } from './useStoreConnectionPlatforms';

const ConnectionPage = () => {
  const storeInfo = useStoreConnectionPlatforms();

  useEffect(storeInfo, []);

  return (
    <S.Section>
      <Spinner size={60} thickness={8} />
      <h1>연결 중입니다 ...</h1>
    </S.Section>
  );
};

export default ConnectionPage;

const S = {
  Section: styled.section`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 4rem;
    width: 100vw;
    height: 100vh;
  `,
};
