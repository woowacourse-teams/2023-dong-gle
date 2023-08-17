import { BlurBackgroundIcon, DonggleIcon } from 'assets/icons';
import LoginModal from 'components/Modal/LoginModal/LoginModal';
import { useModal } from 'hooks/@common/useModal';
import { styled } from 'styled-components';
import donggleExample from 'assets/icons/donggle-example.png';
import { Navigate } from 'react-router-dom';
import { useAuthToken } from 'hooks/useAuthToken';
import { PATH } from 'constants/path';

const IntroducePage = () => {
  const { authToken } = useAuthToken();
  const { isOpen, openModal, closeModal } = useModal();

  if (authToken) return <Navigate to={`${PATH.space}`} />;

  return (
    <S.Container>
      <S.Header>
        <S.Logo>
          <DonggleIcon width={20} height={20} />
          동글
        </S.Logo>
        <S.LoginModalButton onClick={openModal}>로그인하기</S.LoginModalButton>
        <LoginModal isOpen={isOpen} closeModal={closeModal} />
      </S.Header>
      <S.Content>
        <S.Introduce>
          <S.AbsoluteDiv>
            <BlurBackgroundIcon />
          </S.AbsoluteDiv>
          <h1>
            동글에서 블로그 글을
            <br /> 간편하게 포스팅 하세요
          </h1>
          <p>블로그 포스팅뿐만 아니라 글 관리까지 한 번에</p>
          <S.LoginModalButtonLarge onClick={openModal}>동글 시작하기</S.LoginModalButtonLarge>
          <img width={600} src={donggleExample} />
        </S.Introduce>
      </S.Content>
    </S.Container>
  );
};

export default IntroducePage;

const S = {
  AbsoluteDiv: styled.div`
    position: absolute;
    top: -40%;
    opacity: 0.7;
  `,
  Content: styled.section`
    flex: 1;
    display: flex;
    flex-direction: column;
    img {
      border-radius: 4px;
      box-shadow:
        rgba(31, 34, 37, 0.09) 0px 0px 0px 1px,
        rgba(0, 0, 0, 0.04) 0px 24px 48px,
        rgba(0, 0, 0, 0.02) 0px 4px 16px;
      z-index: 2;
    }

    img:hover {
      transition: 0.4s all;
      transform: scale(1.1);
    }
  `,
  Introduce: styled.div`
    display: flex;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: relative;

    gap: 4rem;

    h1 {
      font-size: 4rem;
      text-align: center;
    }
    p {
      font-size: 2rem;
      color: ${({ theme }) => theme.color.gray8};
    }
  `,
  Container: styled.div`
    background: linear-gradient(to top, #ffffff, #fef8ee);

    height: 100%;
    display: flex;
    flex-direction: column;
  `,

  LoginModalButton: styled.button`
    background: linear-gradient(50deg, #eb23f9, #7733ff);
    box-shadow:
      rgba(31, 34, 37, 0.09) 0px 0px 0px 1px,
      rgba(0, 0, 0, 0.04) 0px 24px 48px,
      rgba(0, 0, 0, 0.02) 0px 4px 16px;
    color: #fff;
    width: 11rem;
    height: 3.5rem;
    border-radius: 8px;
    z-index: 2;
  `,
  LoginModalButtonLarge: styled.button`
    background: linear-gradient(50deg, #eb23f9, #7733ff);
    box-shadow:
      rgba(31, 34, 37, 0.09) 0px 0px 0px 1px,
      rgba(0, 0, 0, 0.04) 0px 24px 48px,
      rgba(0, 0, 0, 0.02) 0px 4px 16px;
    color: #ffffffdf;
    width: 20rem;
    font-size: 2rem;
    font-weight: 500;
    height: 5rem;
    border-radius: 8px;
    z-index: 2;
  `,
  Header: styled.header`
    position: sticky;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 10%;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    background-color: #ffffffbc;
    flex: 0 0 5rem;
    z-index: 1;
  `,
  Logo: styled.div`
    display: flex;
    align-items: center;
    gap: 1rem;
    font-size: 2rem;
    font-weight: 900;
  `,
};
