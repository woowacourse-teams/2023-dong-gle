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

export type Writings = {
  id: number;
  title: string;
};

export type GetCategoryDetailsResponse = {
  writings: Writings[];
} & CategoryResponse;

export type PatchCategory = {
  categoryName?: string;
  nextCategoryId?: number;
};

export type PatchCategoryArgs = {
  categoryId: number;
  body: PatchCategory;
};
