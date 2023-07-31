import { getCategoryIdWritingList } from 'apis/writings';
import WritingTable from 'components/WritingTable/WritingTable';
import { useGetQuery } from 'hooks/@common/useGetQuery';
import { PageContext, usePageContext } from 'pages/Layout/Layout';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';
import { GetCategoryIdWritingListResponse } from 'types/apis/writings';

const WritingTablePage = () => {
  const categoryId = Number(useParams()['categoryId']);
  const { isLeftSidebarOpen, isRightSidebarOpen } = usePageContext();

  const { data } = useGetQuery<GetCategoryIdWritingListResponse>({
    fetcher: () => getCategoryIdWritingList(categoryId),
  });

  return (
    <S.Article isLeftSidebarOpen={isLeftSidebarOpen} isRightSidebarOpen={isRightSidebarOpen}>
      <S.CategoryNameTitle>{data?.categoryName}</S.CategoryNameTitle>
      <WritingTable writings={data?.writings ?? []} />
    </S.Article>
  );
};

export default WritingTablePage;

const S = {
  Article: styled.article<PageContext>`
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
