import HomeTable from 'components/HomeTable/HomeTable';
import styled from 'styled-components';

const HomePage = () => {
  return (
    <S.Article>
      <S.CategoryNameTitle>전체 글</S.CategoryNameTitle>
      <HomeTable />
    </S.Article>
  );
};

export default HomePage;

const S = {
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
