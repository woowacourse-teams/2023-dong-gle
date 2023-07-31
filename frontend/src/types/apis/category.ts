import { Writing } from 'components/Category/CategorySection/useCategoryDetails';

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

export type GetCategoryDetailResponse = {
  writings: Writing[] | null;
} & CategoryResponse;

export type PatchCategory = {
  categoryName?: string;
  nextCategoryId?: number;
};

export type PatchCategoryArgs = {
  categoryId: number;
  body: PatchCategory;
};
