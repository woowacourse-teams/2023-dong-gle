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
    width: 50vw;
    height: 20vh;
    max-width: 40rem;
  `,
  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;
  `,
  Content: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 7rem;
    height: 100%;
    margin: 2rem 0;
    font-size: 1.3rem;
  `,
};
