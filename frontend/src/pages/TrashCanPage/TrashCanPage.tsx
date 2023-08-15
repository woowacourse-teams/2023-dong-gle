import { useQuery } from '@tanstack/react-query';
import { getDeletedWritings } from 'apis/trash';
import TrashCanTable from 'components/TrashCanTable/TrashCanTable';
import { styled } from 'styled-components';
import { GetDeletedWritingsResponse } from 'types/apis/trash';

const TrashCanPage = () => {
  const { data, isLoading } = useQuery<GetDeletedWritingsResponse>(
    ['deletedWritings'],
    getDeletedWritings,
    {
      onError: () => alert('휴지통의 글을 불러올 수 없습니다'),
    },
  );

  if (isLoading) return <>로딩 중...</>;

  return (
    <S.Article>
      <S.CategoryNameTitle>휴지통</S.CategoryNameTitle>
      {data ? <TrashCanTable writings={data.writings} /> : <>휴지통이 비었습니다</>}
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
