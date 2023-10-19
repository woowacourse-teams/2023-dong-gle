import { TrashCanEmptyIcon } from 'assets/icons';
import Spinner from 'components/@common/Spinner/Spinner';
import TrashCanTable from 'components/TrashCanTable/TrashCanTable';
import { MAX_WIDTH } from 'constants/style';
import { useDeletedWritings } from 'hooks/useDeletedWritings';
import { css, styled } from 'styled-components';

const TrashCanPage = () => {
  const { deletedWritings, isLoading } = useDeletedWritings();

  if (isLoading) {
    return (
      <S.LoadingContainer>
        <Spinner size={60} thickness={4} />
        <h1>휴지통을 불러오는 중입니다 ...</h1>
      </S.LoadingContainer>
    );
  }

  return (
    <S.Article>
      <S.CategoryNameTitle>휴지통</S.CategoryNameTitle>
      {deletedWritings?.length ? (
        <TrashCanTable writings={deletedWritings} />
      ) : (
        <S.EmptyMessage>
          <TrashCanEmptyIcon width={80} height={80} />
          휴지통이 비어있어요.
        </S.EmptyMessage>
      )}
    </S.Article>
  );
};

export default TrashCanPage;

const generateResponsiveStyle = {
  article: css`
    @media (max-width: 820px) {
      padding: 8rem 2.4rem 0 2.4rem;
    }

    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      padding: 4rem 2.4rem 0 2.4rem;
    }
  `,

  categoryNameTitle: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      font-size: 3.2rem;
      margin-bottom: 4rem;
    }

    @media (max-width: ${MAX_WIDTH.mobileMedium}) {
      font-size: 2.8rem;
      margin-bottom: 4rem;
    }

    @media (max-width: ${MAX_WIDTH.mobileSmall}) {
      font-size: 2.4rem;
      margin-bottom: 4rem;
    }
  `,
};

const S = {
  Article: styled.article`
    position: relative;
    width: 100%;
    padding: 8rem;

    background-color: ${({ theme }) => theme.color.gray1};

    ${() => generateResponsiveStyle.article}
  `,

  CategoryNameTitle: styled.h1`
    font-size: 4rem;
    margin-bottom: 5rem;

    ${() => generateResponsiveStyle.categoryNameTitle}
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
    gap: 2rem;
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
