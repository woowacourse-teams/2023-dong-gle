import Button from 'components/@common/Button/Button';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

const ErrorPage = () => {
  const navigate = useNavigate();

  return (
    <S.Container>
      <S.Status>404 Error</S.Status>
      <S.ErrorMessageContainer>
        <p>요청하신 페이지를 찾을 수 없습니다.</p>
        <p>입력하신 주소가 정확한지 다시 한번 확인해주세요.</p>
      </S.ErrorMessageContainer>
      <Button variant='text' onClick={() => navigate(-1)}>
        이전 페이지로 돌아가기
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
