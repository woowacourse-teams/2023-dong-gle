import Accordion from 'components/@common/Accordion/Accordion';
import { DragEvent, useState } from 'react';
import Category from '../Category/Category';
import WritingList from '../WritingList/WritingList';
import styled, { css } from 'styled-components';

type Props = {
  categoryId: number;
  categoryName: string;
  isDefaultCategory: boolean;
  draggingIndexList: number[];
  dragOverIndexList: number[];
  onDragStart: (...ids: number[]) => (e: DragEvent) => void;
  onDragEnter: (...ids: number[]) => (e: DragEvent) => void;
  onDragEnd: (e: DragEvent) => void;
  isCategoryDrag: () => boolean;
  isWritingDrag: () => boolean;
};

const Item = ({
  categoryId,
  categoryName,
  isDefaultCategory,
  draggingIndexList,
  dragOverIndexList,
  onDragStart,
  onDragEnter,
  onDragEnd,
  isCategoryDrag,
  isWritingDrag,
}: Props) => {
  const [isOpen, setIsOpen] = useState(false);

  const decideDraggingTarget = () => {
    const isCategoryDragOverTarget =
      dragOverIndexList.length === 1 && categoryId === dragOverIndexList[0];

    if (isCategoryDragOverTarget && dragOverIndexList.length !== draggingIndexList.length)
      return 'writing';
    if (isCategoryDragOverTarget) return 'category';
    return 'none';
  };

  return (
    <S.DragContainer
      draggable={!isDefaultCategory}
      $draggingTarget={decideDraggingTarget()}
      onDragStart={onDragStart(categoryId)}
      onDragEnter={onDragEnter(categoryId)}
      onDragEnd={onDragEnd}
    >
      <Accordion.Item key={categoryId}>
        <Accordion.Title
          onIconClick={() => setIsOpen((prev) => !prev)}
          aria-label={`${categoryName} 카테고리 왼쪽 사이드바에서 열기`}
        >
          <Category
            categoryId={categoryId}
            categoryName={categoryName}
            isDefaultCategory={isDefaultCategory}
          />
        </Accordion.Title>
        <Accordion.Panel>
          <WritingList
            categoryId={categoryId}
            isOpen={isOpen}
            dragOverIndexList={dragOverIndexList}
            onDragStart={onDragStart}
            onDragEnter={onDragEnter}
            onDragEnd={onDragEnd}
            isWritingDrag={isWritingDrag}
          />
        </Accordion.Panel>
      </Accordion.Item>
    </S.DragContainer>
  );
};

export default Item;

const S = {
  DragContainer: styled.div<{
    $draggingTarget: 'category' | 'writing' | 'none';
  }>`
    position: relative;
    border-top: 0.4rem solid transparent;

    ${({ $draggingTarget }) =>
      $draggingTarget === 'category' &&
      css`
        border-radius: 0;
        border-top: 0.4rem solid #2383e26e;
      `}

    ${({ $draggingTarget }) =>
      $draggingTarget === 'writing' &&
      css`
        &::before {
          content: '';
          pointer-events: none;
          position: absolute;
          width: 100%;
          height: 100%;
          border-radius: 4px;
          background-color: #2383e26e;
        }
      `}
  `,
};
