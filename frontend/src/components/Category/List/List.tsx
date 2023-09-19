import Accordion from 'components/@common/Accordion/Accordion';
import { useCategories } from './useCategories';
import Item from '../Item/Item';
import { useDragAndDrop } from 'components/Category/useDragAndDrop';
import styled, { css } from 'styled-components';
import { LAST_DRAG_SECTION_ID } from 'constants/drag';

const List = () => {
  const { categories } = useCategories();
  const {
    draggingIndexList,
    dragOverIndexList,
    handleDragEnd,
    handleDragEnter,
    handleDragStart,
    isCategoryDragging,
    isWritingDragging,
  } = useDragAndDrop();

  if (!categories) return null;

  return (
    <Accordion>
      {categories.map((category, index) => {
        return (
          <Item
            key={category.id}
            categoryId={category.id}
            categoryName={category.categoryName}
            isDefaultCategory={Boolean(index === 0)}
            draggingIndexList={draggingIndexList}
            dragOverIndexList={dragOverIndexList}
            onDragStart={handleDragStart}
            onDragEnter={handleDragEnter}
            onDragEnd={handleDragEnd}
            isWritingDragging={isWritingDragging}
          />
        );
      })}
      <S.DragLastSection
        onDragEnter={handleDragEnter(LAST_DRAG_SECTION_ID)}
        $isDragOverTarget={isCategoryDragging && dragOverIndexList[0] === LAST_DRAG_SECTION_ID}
      />
    </Accordion>
  );
};

export default List;

const S = {
  DragLastSection: styled.div<{ $isDragOverTarget: boolean }>`
    height: 0.4rem;
    background-color: transparent;
    ${({ $isDragOverTarget }) =>
      $isDragOverTarget &&
      css`
        background-color: ${({ theme }) => theme.color.dragArea};
      `};
  `,
};
