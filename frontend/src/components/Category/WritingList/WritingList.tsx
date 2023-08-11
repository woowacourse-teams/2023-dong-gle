import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { useWritings } from './useWritings';
import DeleteButton from 'components/DeleteButton/DeleteButton';
import { useMutation } from '@tanstack/react-query';
import { moveToTrash } from 'apis/trash';
import { MouseEventHandler } from 'react';

type Props = {
  categoryId: number;
  isOpen: boolean;
};

const WritingList = ({ categoryId, isOpen }: Props) => {
  const { goWritingPage } = usePageNavigate();
  const { writings } = useWritings(categoryId, isOpen);
  const writingId = Number(useParams()['writingId']);

  const { mutate } = useMutation(moveToTrash, {
    onSuccess: () => alert('글이 휴지통으로 이동됐습니다.'),
    onError: () => alert('글 삭제가 실패했습니다.'),
  });

  const test: MouseEventHandler<HTMLButtonElement> = (e) => {
    e.stopPropagation();
  };

  if (!writings || writings?.length === 0)
    return <S.NoWritingsText>No Writings inside</S.NoWritingsText>;

  return (
    <ul>
      {writings.map((writing) => (
        <S.Item key={writing.id}>
          <S.Button
            $isClicked={writingId === writing.id}
            aria-label={`${writing.title}글 메인화면에 열기`}
            onClick={() => goWritingPage(writing.id)}
          >
            <S.IconWrapper>
              <WritingIcon width={14} height={14} />
            </S.IconWrapper>
            <S.Text>{writing.title}</S.Text>
          </S.Button>
          <S.DeleteButtonWrapper>
            <DeleteButton onClick={() => mutate([categoryId])} />
          </S.DeleteButtonWrapper>
        </S.Item>
      ))}
    </ul>
  );
};

export default WritingList;

const S = {
  Item: styled.li`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    border-radius: 8px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};

      div {
        display: inline-flex;
        flex-shrink: 0;
        gap: 0.8rem;
      }
    }
  `,

  Button: styled.button<{ $isClicked: boolean }>`
    display: flex;
    align-items: center;
    flex: 1;
    gap: 0.8rem;
    min-width: 0;
    height: 3.6rem;
    padding: 0.8rem;
    border-radius: 8px;
    background-color: ${({ theme, $isClicked }) => $isClicked && theme.color.gray5};
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
