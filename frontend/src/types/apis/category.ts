export type AddCategoriesRequest = {
  categoryName: string;
};

type Category = {
  id: number;
  categoryName: string;
};

export type GetCategoriesResponse = {
  categories: Category[];
};

type WritingInCategory = {
  id: number;
  title: string;
};

export type GetWritingsInCategoriesResponse = {
  writings: WritingInCategory[];
} & Category;
