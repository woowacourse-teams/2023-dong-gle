import { categoryURL } from 'constants/apis/url';
import { http } from './fetch';
import {
  AddCategoriesRequest,
  GetCategoriesResponse,
  GetCategoryDetailResponse,
  PatchCategoryArgs,
} from 'types/apis/category';

// POST: 카테고리 추가
export const addCategory = (body: AddCategoriesRequest) => http.post(categoryURL, { json: body });

// GET: 카테고리 목록 조회
export const getCategories = (): Promise<GetCategoriesResponse> => http.get(categoryURL);

// GET: 카테고리 글 목록 조회
export const getWritingsInCategory = (categoryId: number): Promise<GetCategoryDetailResponse> =>
  http.get(`${categoryURL}/${categoryId}`);

// PATCH: 카테고리 수정
export const patchCategory = ({ categoryId, body }: PatchCategoryArgs) =>
  http.patch(`${categoryURL}/${categoryId}`, { json: body });

// DELETE: 카테고리 삭제
export const deleteCategory = (categoryId: number) => http.delete(`${categoryURL}/${categoryId}`);
