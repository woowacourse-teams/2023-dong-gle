let writingId = 1;

export const trashcanWritingTable = {
  writings: [
    {
      id: writingId++,
      title: '너 버려진거야',
      categoryId: 1,
    },
    {
      id: writingId++,
      title: '너 버려진거야2',
      categoryId: 1,
    },
  ],
};

// 글 휴지통으로 이동
export const moveWritingToTrashTable = () => {
  trashcanWritingTable.writings.push({
    id: writingId,
    title: `나도 버려졌어 ${writingId++}`,
    categoryId: 1,
  });
};

// 글 영구 삭제
export const deleteWritingFromTrashTable = (writingIds: number[]) => {
  trashcanWritingTable.writings = trashcanWritingTable.writings.filter(
    ({ id }) => !writingIds.includes(id),
  );
};

// 휴지통에서 글 복구
export const restoreWritingFromTrashTable = (writingIds: number[]) => {
  trashcanWritingTable.writings = trashcanWritingTable.writings.filter(
    ({ id }) => !writingIds.includes(id),
  );
};
