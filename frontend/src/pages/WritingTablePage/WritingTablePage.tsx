import { useQuery } from '@tanstack/react-query';
import { useSetGlobalState } from '@yogjin/react-global-state';
import { getDetailWritings } from 'apis/writings';
import Spinner from 'components/@common/Spinner/Spinner';
import WritingTable from 'components/WritingTable/WritingTable';
import { activeCategoryIdState } from 'globalState';
import use활성화된카테고리설정 from 'hooks/use활성화된카테고리설정';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';

const WritingTablePage = () => {
  const {
    state: { categoryId },
  } = useLocation();

  const { data, isLoading } = useQuery(['detailWritings', categoryId], () =>
    getDetailWritings(categoryId),
  );

  use활성화된카테고리설정(categoryId);

  if (isLoading) {
    return (
      <S.LoadingContainer>
        <Spinner size={60} thickness={4} />
        <h1>카테고리 내 글을 불러오는 중입니다 ...</h1>
      </S.LoadingContainer>
    );
  }

  if (!categoryId) return <div>카테고리가 존재하지 않습니다.</div>;

  return (
    <S.Article>
      <S.CategoryNameTitle>{data?.categoryName}</S.CategoryNameTitle>
      {data?.writings && data.writings.length > 0 ? (
        <WritingTable categoryId={categoryId} writings={data?.writings ?? []} />
      ) : (
        <S.AddWritingText>카테고리에 글을 추가해주세요😊</S.AddWritingText>
      )}
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

  AddWritingText: styled.p`
    font-size: 1.5rem;
  `,

  SidebarSection: styled.section`
    ${sidebarStyle}
  `,

  LoadingContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    max-width: 100%;
    height: 100%;
  `,
};
