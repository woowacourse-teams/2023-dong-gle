import { useQuery } from '@tanstack/react-query';
import { getCategoryIdWritingList } from 'apis/writings';
import WritingTable from 'components/WritingTable/WritingTable';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';

const WritingTablePage = () => {
  const categoryId = Number(useParams()['categoryId']);
  const { data } = useQuery(['category', categoryId, 'writingList'], () =>
    getCategoryIdWritingList(categoryId),
  );

  return (
    <S.Article>
      <S.CategoryNameTitle>{data?.categoryName}</S.CategoryNameTitle>
      <WritingTable writings={data?.writings ?? []} />
    </S.Article>
  );
};

export default WritingTablePage;

const S = {
  Article: styled.article`
    width: 90%;
    padding: 8rem 4rem;

    background-color: ${({ theme }) => theme.color.gray1};
  `,

  CategoryNameTitle: styled.h1`
    font-size: 4rem;
    margin-bottom: 5rem;
  `,

  SidebarSection: styled.section`
    ${sidebarStyle}
  `,
};
