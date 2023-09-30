import { rest } from 'msw';
import { categoryURL } from 'constants/apis/url';
import { categories, getWritingsIn사이드바카테고리 } from 'mocks/data/category';
import {
  AddCategoriesRequest,
  UpdateCategoryOrderArgs,
  UpdateCategoryTitleArgs,
} from 'types/apis/category';
import { hasDefinedField } from 'utils/typeGuard';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { jsonCtx } from './utils';

export const categoryHandlers = [
  // 카테고리 목록 조회
  rest.get(categoryURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(ctx.json(categories), ctx.delay(300), ctx.status(200));
  }),

  // 카테고리 추가
  rest.post(categoryURL, (req, res, ctx) => {
    const body = req.body as AddCategoriesRequest;

    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    if (!body || !body.categoryName)
      return res(
        ctx.delay(300),
        ctx.status(404),
        ctx.json({ message: '카테고리 이름은 공백이 될 수 없습니다.' }),
      );

    return res(ctx.delay(300), ctx.set('Location', `/categories/200`), ctx.status(201));
  }),

  // 카테고리 글 목록 조회
  rest.get(`${categoryURL}/:categoryId`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    const categoryId = Number(req.params.categoryId);

    return res(...jsonCtx(getWritingsIn사이드바카테고리(categoryId)));
  }),

  // 카테고리 수정(이름, 순서)
  rest.patch(`${categoryURL}/:categoryId`, async (req, res, ctx) => {
    const categoryId = Number(req.params.categoryId);
    const body = await req.json();

    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    // 카테고리 순서 변경
    if (hasDefinedField<UpdateCategoryOrderArgs['body']>(body, 'nextCategoryId')) {
      if (!body.nextCategoryId) {
        return res(
          ctx.delay(300),
          ctx.status(404),
          ctx.json({
            message: '카테고리 순서 수정 에러',
          }),
        );
      }
      const newCategories = [...categories.categories];

      const draggingIndex = newCategories.findIndex((category) => category.id === categoryId);

      const draggingItem = newCategories[draggingIndex];
      newCategories.splice(draggingIndex, 1);

      const dragOverIndex = newCategories.findIndex(
        (category) => category.id === body.nextCategoryId,
      );

      if (dragOverIndex === -1) {
        newCategories.push(draggingItem);
      } else {
        newCategories.splice(dragOverIndex, -1, draggingItem);
      }
      categories.categories = newCategories;
    }

    // 카테고리 이름 수정
    if (hasDefinedField<UpdateCategoryTitleArgs['body']>(body, 'categoryName')) {
      if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

      if (!body.categoryName)
        return res(
          ctx.delay(300),
          ctx.status(404),
          ctx.json({
            message: '카테고리 이름 수정 에러',
          }),
        );
    }
    return res(ctx.status(204));
  }),

  // 카테고리 삭제
  rest.delete(`${categoryURL}/:categoryId`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(ctx.delay(300), ctx.status(204));
  }),
];
