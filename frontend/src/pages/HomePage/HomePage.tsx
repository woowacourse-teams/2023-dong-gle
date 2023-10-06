import Spinner from 'components/@common/Spinner/Spinner';
import HomeTable from 'components/HomeTable/HomeTable';
import { Suspense } from 'react';
import styled from 'styled-components';

const HomePage = () => {
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
        <HomeTable />
      </Suspense>
    </S.Article>
  );
};

export default HomePage;

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
    position: relative;
    width: 100%;
    padding: 8rem;

    background-color: ${({ theme }) => theme.color.gray1};
  `,

  CategoryNameTitle: styled.h1`
    font-size: 4rem;
    margin-bottom: 5rem;
  `,
};
