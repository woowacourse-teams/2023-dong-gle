import { useQuery } from '@tanstack/react-query';
import { getDeletedWritings } from 'apis/trash';
import WritingTable from 'components/WritingTable/WritingTable';
import { styled } from 'styled-components';

const TrashCanPage = () => {
  const { data } = useQuery(['deletedWritings'], getDeletedWritings, {
    onError: () => alert('휴지통의 글을 불러올 수 없습니다'),
  });

  return (
    <S.Article>
      <S.CategoryNameTitle>휴지통</S.CategoryNameTitle>
      {/* <WritingTable writings={data?.writings ?? []} /> */}
      <WritingTable writings={[]} />
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
