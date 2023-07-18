import { writingHandlers } from './handlers/writing';

export const handlers = [
  // 글 생성(글 업로드): POST
  rest.post('/writings/file', async (_, res, ctx) => {
    return res(ctx.status(201), ctx.set('Location', `/writings/1`));
  }),

  // 글 블로그로 발행: POST
  rest.post('/writings/:writingId/publish', async (req, res, ctx) => {
    const blog = ['MEDIUM', 'TISTORY'];
    const id = Number(req.params.writingId);
    const { publishTo } = await req.json();

    if (!blog.includes(publishTo) || typeof id !== 'number') return res(ctx.status(404));

    return res(ctx.delay(3000), ctx.status(200));
  }),
];

export const handlers = [...writingHandlers];
