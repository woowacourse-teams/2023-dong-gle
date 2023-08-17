import Button from 'components/@common/Button/Button';
import { ErrorBoundaryFallbackProps } from 'components/ErrorBoundary/ErrorBoundary';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { useTokenError } from './useTokenError';
import { useEffect, useLayoutEffect } from 'react';

const ErrorPage = ({ status, title, message, onResetError }: ErrorBoundaryFallbackProps) => {
  const { goIntroducePage } = usePageNavigate();
  const { handleTokenError } = useTokenError();

  const handleGoIntroducePage = () => {
    onResetError?.();
    goIntroducePage();
  };

  useLayoutEffect(() => {
    if (status === 401) {
      onResetError?.();
      handleTokenError();
    }
  }, []);

  return (
    <S.Container>
      <S.Status>{status} Error</S.Status>
      <S.ErrorMessageContainer>
        <p>요청하신 페이지를 찾을 수 없습니다.</p>
        <p>{message}</p>
      </S.ErrorMessageContainer>
      <Button variant='text' onClick={handleGoIntroducePage}>
        돌아가기
      </Button>
    </S.Container>
  );
};

export default ErrorPage;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 100vh;
    gap: 1rem;
  `,

  Status: styled.h1`
    font-size: 6rem;
    color: ${({ theme }) => theme.color.gray10};
  `,

  ErrorMessageContainer: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 1.5rem;
  `,
};
