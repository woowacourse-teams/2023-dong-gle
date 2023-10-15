export const categories = {
  categories: [
    {
      id: 1,
      categoryName: '기본',
    },
    {
      id: 2,
      categoryName: '쿠마',
    },
    {
      id: 3,
      categoryName: '아커',
    },
    {
      id: 4,
      categoryName: '파인',
    },
  ],
};

const writingsIn사이드바기본카테고리 = {
  id: 1,
  categoryName: '기본',
  writings: [
    {
      id: 1,
      title: '동글을 소개합니다 🎉',
    },
    {
      id: 2,
      title: '백엔드 친구들을 소개합니다(에코, 토리, 비버, 헙크)',
    },
  ],
};

const writingsIn사이드바쿠마카테고리 = {
  id: 2,
  categoryName: '쿠마',
  writings: [
    {
      id: 3,
      title: '곰이란 무엇인가?',
    },
    {
      id: 4,
      title: '쿠마의 일기',
    },
    {
      id: 5,
      title: '곰은 사람을 찢어',
    },
    {
      id: 6,
      title: '겨울잠 잘 자는 법!',
    },
  ],
};

const writingsIn사이드바아커카테고리 = {
  id: 3,
  categoryName: '아커',
  writings: [
    {
      id: 7,
      title: '아이스 아메리카노가 좋은 이유',
    },
    {
      id: 8,
      title: '거북선과 도너츠 잘 만드는 법',
    },
    {
      id: 9,
      title: '소주와 맥주가 1000원 밖에 안하는 용호낙지 솔직 후기',
    },
  ],
};

const writingsIn사이드바파인카테고리 = {
  id: 4,
  categoryName: '파인',
  writings: [
    {
      id: 10,
      title: `I'm fine thank you! and you?`,
    },
    {
      id: 11,
      title: '메이플 아르테일 레벨 별 추천 사냥터 모음 (1 ~ 100)',
    },
    {
      id: 12,
      title: '나는 칼바람 나락이 좋다',
    },
  ],
};

export const getWritingsIn사이드바카테고리 = (categoryId: number) => {
  switch (categoryId) {
    case 1:
      return writingsIn사이드바기본카테고리;
    case 2:
      return writingsIn사이드바쿠마카테고리;
    case 3:
      return writingsIn사이드바아커카테고리;
    case 4:
      return writingsIn사이드바파인카테고리;
  }
};

export const deleteWritingsIn사이드바카테고리 = (writingIds: number[]) => {
  [
    writingsIn사이드바기본카테고리,
    writingsIn사이드바쿠마카테고리,
    writingsIn사이드바아커카테고리,
    writingsIn사이드바파인카테고리,
  ].forEach((카테고리) => {
    카테고리.writings = 카테고리.writings.filter(({ id }) => !writingIds.includes(id));
  });
};

export const deleteCategory = (categoryId: number) => {
  categories.categories = categories.categories.filter(({ id }) => categoryId !== id);

  const category = getWritingsIn사이드바카테고리(categoryId);

  // 카테고리 삭제 시 해당 카테고리 내부 모든 글이 기본 카테고리로 이동
  writingsIn사이드바기본카테고리.writings.push(...(category?.writings ?? []));
};

export const addCategory = (categoryName: string) => {
  categories.categories.push({
    id: Math.max(...categories.categories.map((category) => category.id)) + 1,
    categoryName,
  });
};

export const renameCategory = (categoryId: number, categoryName: string) => {
  categories.categories.forEach((category) => {
    if (category.id === categoryId) {
      category.categoryName = categoryName;
    }
  });
};

export const changeOrder = (categoryId: number, nextCategoryId: number) => {
  const newCategories = [...categories.categories];

  const draggingIndex = newCategories.findIndex((category) => category.id === categoryId);

  const draggingItem = newCategories[draggingIndex];
  newCategories.splice(draggingIndex, 1);

  const dragOverIndex = newCategories.findIndex((category) => category.id === nextCategoryId);

  if (dragOverIndex === -1) {
    newCategories.push(draggingItem);
  } else {
    newCategories.splice(dragOverIndex, -1, draggingItem);
  }
  categories.categories = newCategories;
};

export const addRestoredWritings = (writingIds: number[]) => {
  writingIds.forEach((writingId) =>
    writingsIn사이드바기본카테고리.writings.push({
      id: writingId,
      title: `복구된 글 ${writingId}`,
    }),
  );
};

// 기본 카테고리의 첫 글에서 변경
export const renameWritingTitleIn사이드바기본카테고리 = (newTitle: string) => {
  writingsIn사이드바기본카테고리.writings[0].title = newTitle;
};
