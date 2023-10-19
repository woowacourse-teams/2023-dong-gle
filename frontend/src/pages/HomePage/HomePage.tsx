import { useGlobalStateValue } from '@yogjin/react-global-state';
import Spinner from 'components/@common/Spinner/Spinner';
import HomeTable from 'components/HomeTable/HomeTable';
import { MAX_WIDTH } from 'constants/style';
import { mediaQueryMobileState } from 'globalState';
import { Suspense } from 'react';
import styled, { css } from 'styled-components';

const HomePage = () => {
  const isMobile = useGlobalStateValue(mediaQueryMobileState);

  return (
    <S.Article>
      <Suspense
        fallback={
          <S.LoadingContainer>
            <Spinner size={60} thickness={4} />
            <h1>전체 글을 불러오는 중입니다 ...</h1>
          </S.LoadingContainer>
        }
      >
        <S.CategoryNameTitle>전체 글</S.CategoryNameTitle>
        <HomeTable isMobile={isMobile} />
      </Suspense>
    </S.Article>
  );
};

export default HomePage;

const generateResponsiveStyle = {
  article: css`
    @media (max-width: 820px) {
      padding: 5rem;
    }

    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      padding: 4rem;
    }
  `,

  categoryNameTitle: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      font-size: 3.2rem;
      margin-bottom: 4rem;
    }

    @media (max-width: ${MAX_WIDTH.mobileMedium}) {
      font-size: 2.8rem;
    }

    @media (max-width: ${MAX_WIDTH.mobileSmall}) {
      font-size: 2.4rem;
    }
  `,
};

const S = {
  LoadingContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    max-width: 100%;
    height: 100%;
  `,

  Article: styled.article`
    display: flex;
    flex-direction: column;
    position: relative;
    width: 100%;
    padding: 6rem;

    background-color: ${({ theme }) => theme.color.gray1};

    ${generateResponsiveStyle.article}
  `,

  CategoryNameTitle: styled.h1`
    font-size: 4rem;
    margin-bottom: 5rem;

    ${generateResponsiveStyle.categoryNameTitle}
  `,
};
