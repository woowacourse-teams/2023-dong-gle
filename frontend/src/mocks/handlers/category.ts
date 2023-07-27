import { rest } from 'msw';
import { categoryURL } from 'constants/apis/url';
import { categories, writingsInCategory } from 'mocks/categoryContentsMock';
import { AddCategoriesRequest } from 'types/apis/category';

export const categoryHandlers = [
  // 카테고리 목록 조회
  rest.get(categoryURL, (_, res, ctx) => {
    return res(ctx.json(categories), ctx.delay(300), ctx.status(200));
  }),

  // 카테고리 추가
  rest.post(categoryURL, (req, res, ctx) => {
    const body = req.body as AddCategoriesRequest;

    if (!body || !body.categoryName) return res(ctx.delay(300), ctx.status(404));

    return res(ctx.delay(300), ctx.set('Location', `/categories/200`), ctx.status(201));
  }),

  // 카테고리 글 목록 조회
  rest.get(`${categoryURL}/:categoryId`, (req, res, ctx) => {
    const categoryId = Number(req.params.categoryId);

    if (categoryId !== 200) return res(ctx.delay(300), ctx.status(404));

    return res(ctx.delay(300), ctx.status(200), ctx.json(writingsInCategory));
  }),
];
