export type AddCategoriesRequest = {
  categoryName: string;
};

export type CategoryResponse = {
  id: number;
  categoryName: string;
};

export type GetCategoriesResponse = {
  categories: CategoryResponse[];
};

export type WritingInCategory = {
  id: number;
  title: string;
};

export type GetCategoryDetailsResponse = {
  writings: WritingInCategory[];
} & CategoryResponse;

export type PatchCategory = {
  categoryName?: string;
  nextCategoryId?: number;
};

export type PatchCategoryArgs = {
  categoryId: number;
  body: PatchCategory;
};
