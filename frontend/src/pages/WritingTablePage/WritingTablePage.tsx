import { useQuery } from '@tanstack/react-query';
import { getDetailWritings } from 'apis/writings';
import WritingTable from 'components/WritingTable/WritingTable';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';

const WritingTablePage = () => {
  const categoryId = Number(useParams()['categoryId']);
  const { data } = useQuery(['detailWritings', categoryId], () => getDetailWritings(categoryId));

  return (
    <S.Article>
      <S.CategoryNameTitle>{data?.categoryName}</S.CategoryNameTitle>
      <WritingTable categoryId={categoryId} writings={data?.writings ?? []} />
    </S.Article>
  );
};

export default WritingTablePage;

const S = {
  Article: styled.article`
    width: 100%;
    padding: 8rem;
  `,

  CategoryNameTitle: styled.h1`
    font-size: 4rem;
    margin-bottom: 5rem;
  `,

  SidebarSection: styled.section`
    ${sidebarStyle}
  `,
};
