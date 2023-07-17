// src/mocks/handlers.js
import { rest } from 'msw';

export const handlers = [
  // 글 생성(글 업로드): POST
  rest.post('/writings/file', async (_, res, ctx) => {
    return res(ctx.status(201), ctx.set('Location', `/writings/1`));
  }),
];
