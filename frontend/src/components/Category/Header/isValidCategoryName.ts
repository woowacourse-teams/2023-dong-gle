export const isValidCategoryName = (categoryName: string) => {
  const isValidLength = categoryName.length > 0 && categoryName.length < 31;

  return isValidLength;
};
