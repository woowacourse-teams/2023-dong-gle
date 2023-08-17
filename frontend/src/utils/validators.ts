export const validateCategoryName = (categoryName: string) => {
  if (!(0 < categoryName.length && categoryName.length <= 30)) {
    throw new Error('카테고리 이름은 1자 이상 30자 이하로 입력해주세요.');
  }
};

export const validateWritingTitle = (writingTitle: string) => {
  if (!(0 < writingTitle.length && writingTitle.length <= 255)) {
    throw new Error('글 제목은 1자 이상 255자 이하로 입력해주세요.');
  }
};
