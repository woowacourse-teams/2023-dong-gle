import { useQuery } from '@tanstack/react-query';
import { useGlobalStateValue } from '@yogjin/react-global-state-hook';
import { getDetailWritings } from 'apis/writings';
import WritingTable from 'components/WritingTable/WritingTable';
import { activeCategoryIdState } from 'globalState';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';

const WritingTablePage = () => {
  const activeCategoryId = useGlobalStateValue(activeCategoryIdState);

  if (!activeCategoryId) return <div>카테고리가 존재하지 않습니다.</div>;

  const { data } = useQuery(['detailWritings', activeCategoryId], () =>
    getDetailWritings(activeCategoryId),
  );

  return (
    <S.Article>
      <S.CategoryNameTitle>{data?.categoryName}</S.CategoryNameTitle>
      <WritingTable categoryId={activeCategoryId} writings={data?.writings ?? []} />
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
