import Modal from 'components/@common/Modal/Modal';
import KakaoLoginButton from 'components/Login/KakaoLoginButton';
import { MAX_WIDTH } from 'constants/style';
import { css, styled } from 'styled-components';

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

const generateResponsiveStyle = {
  container: css`
    @media (max-width: ${MAX_WIDTH.tablet}) {
      width: 360px;
    }

    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      width: 60vw;
      max-width: 360px;
    }
  `,

  title: css`
    @media (max-width: ${MAX_WIDTH.mobileSmall}) {
      font-size: 2rem;
      font-weight: 600;
    }
  `,

  content: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      margin: 1.6rem 0;
    }

    @media (max-width: ${MAX_WIDTH.mobileSmall}) {
      margin: 1.2rem 0;
    }
  `,
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
    width: 360px;
    height: 20vh;

    ${generateResponsiveStyle.container}
  `,

  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;

    ${generateResponsiveStyle.title}
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
  `,
};
