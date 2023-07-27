import { getCategoryIdWritingList } from 'apis/writings';
import WritingTable from 'components/WritingTable/WritingTable';
import { useGetQuery } from 'hooks/@common/useGetQuery';
import { PageContextType, usePageContext } from 'pages/Layout/Layout';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { LAYOUT_STYLE, genMainPageWidth, sidebarStyle } from 'styles/layoutStyle';
import { GetCategoryIdWritingListResponse } from 'types/apis/writings';

const WritingTablePage = () => {
  const categoryId = Number(useParams()['categoryId']);
  const { isLeftSidebarOpen, isRightSidebarOpen } = usePageContext();

  const { data } = useGetQuery<GetCategoryIdWritingListResponse>({
    fetcher: () => getCategoryIdWritingList(categoryId),
  });

  return (
    <S.Container>
      <S.Article isLeftSidebarOpen={isLeftSidebarOpen} isRightSidebarOpen={isRightSidebarOpen}>
        <S.CategoryNameTitle>{data?.categoryName}</S.CategoryNameTitle>
        <WritingTable writings={data?.writings ?? []} />
      </S.Article>
    </S.Container>
  );
};

export default WritingTablePage;

const S = {
  Container: styled.div`
    display: flex;
    gap: ${LAYOUT_STYLE.gap};
    height: 100%;
  `,

  Article: styled.article<PageContextType>`
    ${({ isLeftSidebarOpen, isRightSidebarOpen }) =>
      genMainPageWidth(isLeftSidebarOpen, isRightSidebarOpen)};
    flex: 1;
    border: ${LAYOUT_STYLE.border};
    border-radius: 8px;
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
