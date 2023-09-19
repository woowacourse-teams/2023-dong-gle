import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useParams } from 'react-router-dom';
import { styled, css } from 'styled-components';
import { useWritings } from './useWritings';
import DeleteButton from 'components/DeleteButton/DeleteButton';
import { useDeleteWritings } from './useDeleteWritings';
import { DragEvent } from 'react';
import { LAST_DRAG_SECTION_ID } from 'constants/drag';

type Props = {
  categoryId: number;
  isOpen: boolean;
  dragOverIndexList: number[];
  onDragStart: (...ids: number[]) => (e: DragEvent) => void;
  onDragEnter: (...ids: number[]) => (e: DragEvent) => void;
  onDragEnd: (e: DragEvent) => void;
  isWritingDragging: boolean;
};

const WritingList = ({
  categoryId,
  isOpen,
  dragOverIndexList,
  onDragStart,
  onDragEnter,
  onDragEnd,
  isWritingDragging,
}: Props) => {
  const { goWritingPage } = usePageNavigate();
  const { writings } = useWritings(categoryId, isOpen);
  const writingId = Number(useParams()['writingId']);
  const deleteWritings = useDeleteWritings();

  const isWritingDragOverTarget = (categoryId: number, writingId: number) =>
    isWritingDragging && categoryId === dragOverIndexList[0] && writingId === dragOverIndexList[1];

  if (!writings || writings?.length === 0) return <S.NoWritingsText>빈 카테고리</S.NoWritingsText>;

  return (
    <ul>
      {writings.map((writing) => (
        <S.Item
          key={writing.id}
          $isClicked={writingId === writing.id}
          $isDragOverTarget={isWritingDragOverTarget(categoryId, writing.id)}
          draggable={true}
          onDragStart={onDragStart(categoryId, writing.id)}
          onDragEnter={onDragEnter(categoryId, writing.id)}
          onDragEnd={onDragEnd}
        >
          <S.Button
            aria-label={`${writing.title}글 메인화면에 열기`}
            onClick={() => goWritingPage({ categoryId, writingId: writing.id })}
          >
            <S.IconWrapper>
              <WritingIcon width={14} height={14} />
            </S.IconWrapper>
            <S.Text>{writing.title}</S.Text>
          </S.Button>
          <S.DeleteButtonWrapper>
            <DeleteButton onClick={() => deleteWritings([writing.id])} />
          </S.DeleteButtonWrapper>
        </S.Item>
      ))}
      <S.DragLastSection
        onDragEnter={onDragEnter(categoryId, LAST_DRAG_SECTION_ID)}
        $isDragOverTarget={isWritingDragOverTarget(categoryId, LAST_DRAG_SECTION_ID)}
      />
    </ul>
  );
};

export default WritingList;

const S = {
  Item: styled.li<{ $isClicked: boolean; $isDragOverTarget: boolean }>`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 3.6rem;
    border-radius: 4px;
    background-color: ${({ theme, $isClicked }) => $isClicked && theme.color.gray4};
    border-top: 0.4rem solid transparent;

    ${({ $isDragOverTarget }) =>
      $isDragOverTarget &&
      css`
        border-radius: 0;
        border-top: 0.4rem solid #2383e26e;
      `};
    &:hover {
      background-color: ${({ theme }) => theme.color.gray3};

      div {
        display: flex;
        flex-shrink: 0;
      }
    }
  `,

  Button: styled.button`
    display: flex;
    align-items: center;
    gap: 0.4rem;
    min-width: 0;
    height: 100%;
    padding: 0.4rem 0 0.4rem 3.2rem;
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
    padding: 0.4rem 0 0.4rem 3.2rem;
    color: ${({ theme }) => theme.color.gray6};
    font-size: 1.4rem;
    font-weight: 500;
    cursor: default;
  `,

  DeleteButtonWrapper: styled.div`
    display: none;
    margin-right: 0.8rem;
  `,

  DragLastSection: styled.div<{ $isDragOverTarget: boolean }>`
    height: 0.4rem;
    background-color: transparent;
    ${({ $isDragOverTarget }) =>
      $isDragOverTarget &&
      css`
        background-color: #2383e26e;
      `};
  `,
};
