import { useCategoryMutation } from 'components/Category/useCategoryMutation';
import { INDEX_POSITION, LAST_DRAG_SECTION_ID } from 'constants/drag';
import { useWritingOrderUpdate } from 'hooks/queries/writing/useWritingOrderUpdate';
import { DragEventHandler, useState } from 'react';
import { isSameArray } from 'utils/array';

export const useDragAndDrop = () => {
  const { updateCategoryOrder } = useCategoryMutation();
  const updateWritingOrder = useWritingOrderUpdate();
  const [draggingIndexList, setDraggingIndexList] = useState<number[]>([]);
  const [dragOverIndexList, setDragOverIndexList] = useState<number[]>([]);

  // 드래그 시작할 때.
  // 카테고리면 인수 1개(categoryId), 글이면 인수가 2개(categoryId, writingId).
  const handleDragStart =
    (...ids: number[]): DragEventHandler =>
    (e) => {
      e.stopPropagation();

      setDraggingIndexList(ids);
    };

  // 드래그 하고 있는 대상이 드래그 가능한 영역에 들어올 때.
  const handleDragEnter =
    (...ids: number[]): DragEventHandler =>
    (e) => {
      e.stopPropagation();

      // 카테고리 선택하고 글 위에 마우스를 올려도 카테고리로 인식하기 위함.
      const dragOverIndex = ids.slice(0, draggingIndexList.length);

      // 원래 위치로는 순서 바꾸기 불가.
      const isSamePositionDrag = isSameArray(draggingIndexList, dragOverIndex);

      // 기본 카테고리 위로는 순서 바꾸기 불가.
      const isDefaultCategoryDrag =
        isCategoryDragging &&
        dragOverIndex[INDEX_POSITION.CATEGORY_ID] ===
          Number(localStorage.getItem('defaultCategoryId'));

      const isDragImpossible = isSamePositionDrag || isDefaultCategoryDrag;

      setDragOverIndexList(isDragImpossible ? [] : dragOverIndex);
    };

  // 마우스를 놓는 순간 발생.
  const handleDragEnd: DragEventHandler = (e) => {
    e.stopPropagation();

    if (draggingIndexList.length === 0 || dragOverIndexList.length === 0) return;
    // 카테고리 이동
    else if (isCategoryDragging) {
      updateCategoryOrder({
        categoryId: draggingIndexList[INDEX_POSITION.CATEGORY_ID],
        body: {
          nextCategoryId: dragOverIndexList[INDEX_POSITION.CATEGORY_ID],
        },
      });
    }

    // 글 이동
    else if (isWritingDragging) {
      // 카테고리로 이동했을 때 마지막(-1)으로 이동시키기 위함.
      const nextWritingId =
        dragOverIndexList.length === 1
          ? LAST_DRAG_SECTION_ID
          : dragOverIndexList[INDEX_POSITION.WRITING_ID];

      updateWritingOrder.mutate({
        writingId: draggingIndexList[INDEX_POSITION.WRITING_ID],
        body: {
          targetCategoryId: dragOverIndexList[INDEX_POSITION.CATEGORY_ID],
          nextWritingId,
        },
      });
    }

    setDraggingIndexList([]);
    setDragOverIndexList([]);
  };

  const isCategoryDragging = draggingIndexList.length === 1;

  const isWritingDragging = draggingIndexList.length === 2;

  return {
    draggingIndexList,
    dragOverIndexList,
    handleDragStart,
    handleDragEnter,
    handleDragEnd,
    isCategoryDragging,
    isWritingDragging,
  };
};
