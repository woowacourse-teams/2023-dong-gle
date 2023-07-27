import { categoryURL } from 'constants/apis/url';
import { http } from './fetch';
import { AddCategoriesRequest } from 'types/apis/category';

// POST: 카테고리 추가
export const addCategory = (body: AddCategoriesRequest) =>
  http.post(categoryURL, { body: JSON.stringify(body) });

// GET: 카테고리 목록 조회
export const getCategories = () => http.get(categoryURL);

// GET: 카테고리 글 목록 조회
export const getWritingsInCategory = (categoryId: number) =>
  http.get(`${categoryURL}/${categoryId}`);
