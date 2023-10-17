import Modal from 'components/@common/Modal/Modal';
import KakaoLoginButton from 'components/Login/KakaoLoginButton';
import { styled } from 'styled-components';

type Props = {
  isOpen: boolean;
  closeModal: () => void;
};

const LoginModal = ({ isOpen, closeModal }: Props) => {
  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <S.Container>
        <S.Title>간편 로그인</S.Title>
        <S.Content>
          <KakaoLoginButton />
        </S.Content>
      </S.Container>
    </Modal>
  );
};

export default LoginModal;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
    width: 30vw;
    max-width: 40vw;
    height: 20vh;

    @media (max-width: 1080px) {
      width: 50vw;
    }
  `,

  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;

    @media (max-width: 320px) {
      font-size: 2rem;
      font-weight: 600;
    }
  `,

  Content: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 7rem;
    width: 100%;
    height: 100%;
    margin: 2rem 0;
    font-size: 1.3rem;

    @media (max-width: 768px) {
      margin: 1.6rem 0;
    }

    @media (max-width: 320px) {
      margin: 1.2rem 0;
    }
  `,
};
