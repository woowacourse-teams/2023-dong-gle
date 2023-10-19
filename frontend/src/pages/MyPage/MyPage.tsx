import { LeftArrowHeadIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import ConnectionSection from 'components/ConnectionSection/ConnectionSection';
import Profile from 'components/Profile/Profile';
import { NAVIGATE_PATH } from 'constants/path';
import { MAX_WIDTH } from 'constants/style';
import { useMember } from 'hooks/queries/useMember';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { Link } from 'react-router-dom';
import { css, styled } from 'styled-components';

const MyPage = () => {
  const { isLoading, name, tistory, medium, notion } = useMember();
  const { goSpacePage } = usePageNavigate();

  if (isLoading)
    return (
      <S.SpinnerContainer>
        <Spinner size={48} thickness={6} />
        <p>마이 페이지로 이동 중입니다...</p>
      </S.SpinnerContainer>
    );

  if (!name || !tistory || !medium || !notion) return null;

  return (
    <S.Section>
      <S.Header>
        <S.GoToSpacePage to={NAVIGATE_PATH.spacePage}>
          {<LeftArrowHeadIcon width={20} height={20} />}
        </S.GoToSpacePage>
        <S.Title>마이 페이지</S.Title>
        <Button variant='secondary' size='small' onClick={goSpacePage}>
          스페이스로 가기
        </Button>
      </S.Header>
      <S.Container $isLoading={isLoading}>
        {!isLoading ? (
          <>
            <Profile name={name} />
            <S.ContentContainer>
              <ConnectionSection tistory={tistory} medium={medium} notion={notion} />
            </S.ContentContainer>
          </>
        ) : (
          <Spinner size={60} thickness={8} />
        )}
      </S.Container>
    </S.Section>
  );
};

export default MyPage;

const generateResponsiveStyle = {
  header: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      position: relative;
      justify-content: center;

      & > a {
        display: block;
      }

      & > button {
        display: none;
      }
    }
  `,

  container: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      flex-direction: column;
    }
  `,

  contentContainer: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      gap: 4rem;
      width: 100%;
      padding: 2.4rem;
    }
  `,
};

const S = {
  Section: styled.section`
    width: 100vw;
    height: 100vh;
  `,

  Header: styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 4rem;

    ${generateResponsiveStyle.header}
  `,

  GoToSpacePage: styled(Link)`
    position: absolute;
    left: 2rem;
    display: none;
  `,

  Title: styled.h1`
    display: flex;
    align-items: center;
    height: 10rem;
    font-size: 3.2rem;
  `,

  Container: styled.div<{ $isLoading: boolean }>`
    display: flex;
    justify-content: ${({ $isLoading }) => ($isLoading ? 'center' : 'none')};
    align-items: ${({ $isLoading }) => ($isLoading ? 'center' : 'none')};
    width: 100%;
    height: calc(100% - 12rem);
    border-top: 1px solid ${({ theme }) => theme.color.gray5};

    ${generateResponsiveStyle.container}
  `,

  ContentContainer: styled.div`
    display: flex;
    flex-direction: column;
    gap: 8rem;
    width: 70%;
    padding: 4rem;
    font-size: 1.2rem;

    ${generateResponsiveStyle.contentContainer}
  `,

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
