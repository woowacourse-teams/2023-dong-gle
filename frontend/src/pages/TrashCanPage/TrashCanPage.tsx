import TrashCanTable from 'components/TrashCanTable/TrashCanTable';
import { useDeletedWritings } from 'hooks/useDeletedWritings';
import { styled } from 'styled-components';

const TrashCanPage = () => {
  const { deletedWritings, isLoading } = useDeletedWritings();

  if (isLoading) return <>로딩 중...</>;

  return (
    <S.Article>
      <S.CategoryNameTitle>휴지통</S.CategoryNameTitle>
      {deletedWritings ? <TrashCanTable writings={deletedWritings} /> : <>휴지통이 비었습니다</>}
    </S.Article>
  );
};

export default TrashCanPage;

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
};
