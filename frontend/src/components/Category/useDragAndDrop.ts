import { useCategoryMutation } from 'components/Category/useCategoryMutation';
import { useWritingOrderUpdate } from 'hooks/queries/writing/useWritingOrderUpdate';
import { DragEventHandler, useState } from 'react';
import { isSameArray } from 'utils/array';

export const useDragAndDrop = () => {
  const { updateCategoryOrder } = useCategoryMutation();
  const updateWritingOrder = useWritingOrderUpdate();
  const [draggingIndexList, setDraggingIndexList] = useState<number[]>([]);
  const [dragOverIndexList, setDragOverIndexList] = useState<number[]>([]);
  const [defaultCategoryId, setDefaultCategoryId] = useState(-1);

  // 드래그 시작할 때.
  // 카테고리면 인수 1개(categoryId), 글이면 인수가 2개(categoryId, writingId).
  const handleDragStart =
    (...ids: number[]): DragEventHandler =>
    (e) => {
      if (e.currentTarget === e.target) setDraggingIndexList(ids);
    };

  // 드래그 하고 있는 대상이 드래그 가능한 영역에 들어올 때.
  const handleDragEnter =
    (...ids: number[]): DragEventHandler =>
    (e) => {
      e.stopPropagation();

      // 카테고리 선택하고 글 위에 마우스를 올려도 카테고리로 인식하기 위함.
      const dragOverIndex = ids.slice(0, draggingIndexList.length);

      // 원래 위치로는 순서 바꾸기 불가.
      if (isSameArray(draggingIndexList, dragOverIndex)) {
        setDragOverIndexList([]);
        return;
      }

      // 기본 카테고리 위로는 순서 바꾸기 불가.
      if (isCategoryDragging && dragOverIndex[0] === defaultCategoryId) {
        setDragOverIndexList([]);
        return;
      }
      setDragOverIndexList(dragOverIndex);
    };

  // 마우스를 놓는 순간 발생.
  const handleDragEnd: DragEventHandler = (e) => {
    e.stopPropagation();

    if (draggingIndexList.length === 0 || dragOverIndexList.length === 0) return;
    // 카테고리 이동
    else if (isCategoryDragging) {
      updateCategoryOrder({
        categoryId: draggingIndexList[0],
        body: {
          nextCategoryId: dragOverIndexList[0],
        },
      });
    }

    // 글 이동
    else if (isWritingDragging) {
      // 카테고리로 이동했을 때 마지막(-1)으로 이동시키기 위함.
      const nextWritingId = dragOverIndexList.length === 1 ? -1 : dragOverIndexList[1];

      updateWritingOrder.mutate({
        writingId: draggingIndexList[1],
        body: {
          targetCategoryId: dragOverIndexList[0],
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
    setDefaultCategoryId,
    draggingIndexList,
    dragOverIndexList,
    handleDragStart,
    handleDragEnter,
    handleDragEnd,
    isCategoryDragging,
    isWritingDragging,
  };
};
