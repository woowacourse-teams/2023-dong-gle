import { rest } from 'msw';
import { categoryURL } from 'constants/apis/url';
import {
  addCategory,
  categories,
  changeOrder,
  deleteCategory,
  getWritingsIn사이드바카테고리,
  renameCategory,
} from 'mocks/data/category';
import {
  AddCategoriesRequest,
  UpdateCategoryOrderArgs,
  UpdateCategoryTitleArgs,
} from 'types/apis/category';
import { hasDefinedField } from 'utils/typeGuard';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { errorCtx, jsonCtx, withoutJson } from './utils';

export const categoryHandlers = [
  // 카테고리 목록 조회
  rest.get(categoryURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(...jsonCtx(categories));
  }),

  // 카테고리 추가
  rest.post(categoryURL, async (req, res, ctx) => {
    const body = await req.json<AddCategoriesRequest>();

    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    addCategory(body.categoryName);

    return res(ctx.delay(300), ctx.set('Location', `/categories/200`), ctx.status(201));
  }),

  // 카테고리 글 목록 조회
  rest.get(`${categoryURL}/:categoryId`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    const categoryId = Number(req.params.categoryId);

    return res(...jsonCtx(getWritingsIn사이드바카테고리(categoryId)));
  }),

  // 카테고리 수정(이름, 순서)
  rest.patch(`${categoryURL}/:categoryId`, async (req, res, ctx) => {
    const categoryId = Number(req.params.categoryId);
    const body = await req.json();

    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    // 카테고리 순서 변경
    if (hasDefinedField<UpdateCategoryOrderArgs['body']>(body, 'nextCategoryId')) {
      changeOrder(categoryId, body.nextCategoryId);
    }

    // 카테고리 이름 수정
    if (hasDefinedField<UpdateCategoryTitleArgs['body']>(body, 'categoryName')) {
      if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

      renameCategory(categoryId, body.categoryName);
    }
    return res(...withoutJson(204));
  }),

  // 카테고리 삭제
  rest.delete(`${categoryURL}/:categoryId`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    deleteCategory(Number(req.params.categoryId));

    return res(...withoutJson(204));
  }),
];
