import { CategoryWriting } from 'types/components/category';

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
  writings: CategoryWriting[] | null;
} & CategoryResponse;

export type UpdateCategoryTitleArgs = {
  categoryId: number;
  body: AddCategoriesRequest;
};

export type UpdateCategoryOrderArgs = {
  categoryId: number;
  body: { nextCategoryId: number };
};
