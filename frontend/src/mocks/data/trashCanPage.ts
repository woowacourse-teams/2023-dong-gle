export const trashcanWritingTable = {
  writings: [
    {
      id: 13,
      title: '너 버려진거야',
      categoryId: 1,
    },
  ],
};

export const addDeletedWriting = () => {
  trashcanWritingTable.writings.push({
    id: 14,
    title: '나도 버려졌어',
    categoryId: 1,
  });
};
