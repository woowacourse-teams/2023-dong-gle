import { blogsURL } from 'constants/apis/url';
import { http } from './fetch';
import { GetTistoryCategoriesResponse } from 'types/apis/blogs';

// GET: 티스토리 카테고리 요청
export const getTistoryCategories = (): Promise<GetTistoryCategoriesResponse> =>
  http.get(`${blogsURL}/tistory/category`);
