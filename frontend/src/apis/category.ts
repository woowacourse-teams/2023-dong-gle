import { categoryURL } from 'constants/apis/url';
import { http } from './fetch';
import { AddCategoriesRequest, PatchCategoryArgs } from 'types/apis/category';

// POST: 카테고리 추가
export const addCategory = (body: AddCategoriesRequest) =>
  http.post(categoryURL, {
    body: JSON.stringify(body),
    headers: {
      'Content-Type': 'application/json',
    },
  });

// GET: 카테고리 목록 조회
export const getCategories = () => http.get(categoryURL);

// GET: 카테고리 글 목록 조회
export const getWritingsInCategory = (categoryId: number | null) =>
  http.get(`${categoryURL}/${categoryId}`);

// PATCH: 카테고리 수정
export const patchCategory = ({ categoryId, body }: PatchCategoryArgs) =>
  http.patch(`${categoryURL}/${categoryId}`, {
    body: JSON.stringify(body),
    headers: {
      'Content-Type': 'application/json',
    },
  });

// DELETE: 카테고리 삭제
export const deleteCategory = (categoryId: number) => http.delete(`${categoryURL}/${categoryId}`);
