import { TrashCanEmptyIcon } from 'assets/icons';
import TrashCanTable from 'components/TrashCanTable/TrashCanTable';
import { useDeletedWritings } from 'hooks/useDeletedWritings';
import { styled } from 'styled-components';

const TrashCanPage = () => {
  const { deletedWritings, isLoading } = useDeletedWritings();

  if (isLoading) return <>로딩 중...</>;

  return (
    <S.Article>
      <S.CategoryNameTitle>휴지통</S.CategoryNameTitle>
      {deletedWritings?.length ? (
        <TrashCanTable writings={deletedWritings} />
      ) : (
        <S.EmptyMessage>
          <TrashCanEmptyIcon width={36} height={36} />
          휴지통이 비어있어요.
        </S.EmptyMessage>
      )}
    </S.Article>
  );
};

export default TrashCanPage;

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

  EmptyMessage: styled.p`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 1.6rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1.5rem;
  `,
};
