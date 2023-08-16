import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import DeleteButton from 'components/DeleteButton/DeleteButton';
import { useDeletedWritings } from 'hooks/useDeletedWritings';
import { useDeletePermanentWritings } from 'components/TrashCanTable/useDeletePermanentWritings';

const DeletedWritingList = () => {
  const { deletedWritings } = useDeletedWritings();
  const { goWritingPage } = usePageNavigate();
  const writingId = Number(useParams()['writingId']);
  const deletePermanentWritings = useDeletePermanentWritings();

  if (!deletedWritings || deletedWritings?.length === 0)
    return <S.NoWritingsText>No Writings inside</S.NoWritingsText>;

  return (
    <ul>
      {deletedWritings.map((deletedWriting) => (
        <S.Item key={deletedWriting.id} $isClicked={writingId === deletedWriting.id}>
          <S.Button
            aria-label={`${deletedWriting.title}글 메인화면에 열기`}
            onClick={() =>
              goWritingPage({ categoryId: deletedWriting.categoryId, writingId: deletedWriting.id })
            }
          >
            <S.IconWrapper>
              <WritingIcon width={14} height={14} />
            </S.IconWrapper>
            <S.Text>{deletedWriting.title}</S.Text>
          </S.Button>
          <S.DeleteButtonWrapper>
            <DeleteButton onClick={() => deletePermanentWritings([writingId])} />
          </S.DeleteButtonWrapper>
        </S.Item>
      ))}
    </ul>
  );
};

export default DeletedWritingList;

const S = {
  Item: styled.li<{ $isClicked: boolean }>`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 3.6rem;
    border-radius: 4px;
    background-color: ${({ theme, $isClicked }) => $isClicked && theme.color.gray4};

    &:hover {
      background-color: ${({ theme }) => theme.color.gray4};

      div {
        display: inline-flex;
        flex-shrink: 0;
        gap: 0.8rem;
      }
    }
  `,

  Button: styled.button`
    display: flex;
    align-items: center;
    gap: 0.4rem;
    min-width: 0;
    height: 100%;
    padding: 0.4rem 0 0.4rem 0.8rem;
    border-radius: 4px;
  `,

  IconWrapper: styled.div`
    flex-shrink: 0;
  `,

  Text: styled.p`
    color: ${({ theme }) => theme.color.gray9};
    font-size: 1.4rem;
    font-weight: 400;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  NoWritingsText: styled.p`
    padding: 0.8rem;
    color: ${({ theme }) => theme.color.gray6};
    font-size: 1.4rem;
    font-weight: 500;
    cursor: default;
  `,

  DeleteButtonWrapper: styled.div`
    display: none;
    margin-right: 0.8rem;
  `,
};
