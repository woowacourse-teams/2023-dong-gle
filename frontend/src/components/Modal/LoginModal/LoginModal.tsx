import { useGlobalStateValue } from '@yogjin/react-global-state';
import Modal from 'components/@common/Modal/Modal';
import KakaoLoginButton from 'components/Login/KakaoLoginButton';
import { MAX_WIDTH } from 'constants/style';
import { mediaQueryMobileState } from 'globalState';
import { css, styled } from 'styled-components';

type Props = {
  isOpen: boolean;
  closeModal: () => void;
};

const LoginModal = ({ isOpen, closeModal }: Props) => {
  const isMobile = useGlobalStateValue(mediaQueryMobileState);

  return (
    <Modal isOpen={isOpen} closeModal={closeModal} hasCloseButton={!isMobile}>
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
  container: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.tablet}) {
        width: 50vw;
      }
    `;
  },

  title: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileSmall}) {
        font-size: 2rem;
        font-weight: 600;
      }
    `;
  },

  content: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        margin: 1.6rem 0;
      }

      @media (max-width: ${MAX_WIDTH.mobileSmall}) {
        margin: 1.2rem 0;
      }
    `;
  },
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
    width: 30vw;
    max-width: 40vw;
    height: 20vh;

    ${() => generateResponsiveStyle.container()}
  `,

  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;

    ${() => generateResponsiveStyle.title()}
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
