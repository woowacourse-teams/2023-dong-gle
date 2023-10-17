import { BlurBackgroundIcon, DonggleIcon } from 'assets/icons';
import LoginModal from 'components/Modal/LoginModal/LoginModal';
import { useModal } from 'hooks/@common/useModal';
import { styled } from 'styled-components';
import donggleExamplePng from 'assets/icons/donggle-example-png.png';
import donggleExampleWebp from 'assets/icons/donggle-example-webp.webp';
import donggleExampleAvif from 'assets/icons/donggle-example-avif.avif';
import donggleExamplePng2X from 'assets/icons/donggle-example-png-2x.png';
import donggleExampleWebp2X from 'assets/icons/donggle-example-webp-2x.webp';
import donggleExampleAvif2X from 'assets/icons/donggle-example-avif-2x.avif';
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
        <S.SmallLoginButton onClick={openModal}>로그인</S.SmallLoginButton>
        <LoginModal isOpen={isOpen} closeModal={closeModal} />
      </S.Header>
      <S.Content>
        <S.Introduce>
          <S.AbsoluteDiv>
            <BlurBackgroundIcon />
          </S.AbsoluteDiv>
          <S.Title>
            동글에서 블로그 글을 <br /> 간편하게 포스팅 하세요
          </S.Title>
          <S.Description>블로그 포스팅뿐만 아니라 글 관리까지 한 번에</S.Description>
          <S.LargeLoginButton onClick={openModal}>동글 시작하기</S.LargeLoginButton>
          <S.Picture>
            <source
              type='image/avif'
              srcSet={`${donggleExampleAvif}, ${donggleExampleAvif2X} 2x`}
            />
            <source
              type='image/webp'
              srcSet={`${donggleExampleWebp}, ${donggleExampleWebp2X} 2x`}
            />
            <img
              src={donggleExamplePng}
              srcSet={`${donggleExamplePng}, ${donggleExamplePng2X} 2x`}
            />
          </S.Picture>
        </S.Introduce>
      </S.Content>
    </S.Container>
  );
};

export default IntroducePage;

const SmallLoginButton = styled.button`
  background: linear-gradient(50deg, #eb23f9, #7733ff);
  box-shadow:
    rgba(31, 34, 37, 0.09) 0px 0px 0px 1px,
    rgba(0, 0, 0, 0.04) 0px 24px 48px,
    rgba(0, 0, 0, 0.02) 0px 4px 16px;
  color: #ffffffdf;
  border-radius: 8px;
  z-index: 2;
`;

const S = {
  Container: styled.div`
    background: linear-gradient(to top, #ffffff, #fef8ee);

    height: 100%;
    display: flex;
    flex-direction: column;
  `,

  Header: styled.header`
    position: sticky;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 5%;
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

  SmallLoginButton: styled(SmallLoginButton)`
    width: 11rem;
    height: 3.5rem;
    font-size: 1.2rem;

    @media (max-width: 768px) {
      width: 8rem;
      height: 3rem;
      font-size: 1rem;
    }

    @media (max-width: 320px) {
      width: 4rem;
      height: 4rem;
      font-size: 0.8rem;
      border-radius: 50%;
    }
  `,

  LargeLoginButton: styled(SmallLoginButton)`
    width: 20rem;
    height: 5rem;
    font-size: 2rem;
    font-weight: 500;

    @media (max-width: 768px) {
      width: 16rem;
      height: 4rem;
      font-size: 1.6rem;
    }

    @media (max-width: 320px) {
      width: 12rem;
      height: 3rem;
      font-size: 1.2rem;
    }
  `,

  Content: styled.section`
    flex: 1;
    display: flex;
    flex-direction: column;
  `,

  Introduce: styled.div`
    display: flex;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: relative;

    gap: 4rem;

    @media (max-width: 768px) {
      gap: 3rem;
    }

    @media (max-width: 320px) {
      gap: 2rem;
    }
  `,

  AbsoluteDiv: styled.div`
    position: absolute;
    top: -40%;
    opacity: 0.7;
  `,

  Title: styled.h1`
    font-size: 4rem;
    text-align: center;

    @media (max-width: 768px) {
      font-size: 3.2rem;
    }

    @media (max-width: 320px) {
      font-size: 2rem;
    }
  `,

  Description: styled.p`
    font-size: 2rem;
    color: ${({ theme }) => theme.color.gray8};

    @media (max-width: 768px) {
      font-size: 1.6rem;
    }

    @media (max-width: 320px) {
      font-size: 1.2rem;
    }
  `,

  Picture: styled.picture`
    display: flex;
    justify-content: center;
    width: 100%;
    z-index: 2;

    & > img {
      width: 70%;
      border-radius: 4px;
      box-shadow:
        rgba(31, 34, 37, 0.09) 0px 0px 0px 1px,
        rgba(0, 0, 0, 0.04) 0px 24px 48px,
        rgba(0, 0, 0, 0.02) 0px 4px 16px;

      &:hover {
        transition: 0.4s all;
        transform: scale(1.1);
      }
    }
  `,
};
