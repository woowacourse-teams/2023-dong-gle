import { rest } from 'msw';
import { writingURL } from 'constants/apis/url';
import { writingContentMock } from 'mocks/writingContentMock';
import { GetWritingResponse } from 'types/apis/writings';

export const writingHandlers = [
  rest.get(`${writingURL}/:writingId`, (req, res, ctx) => {
    const writingId = Number(req.params.writingId);

    if (writingId === 200) {
      return res(
        ctx.delay(300),
        ctx.status(200),
        ctx.json<GetWritingResponse>({
          id: writingId,
          title: '테스트 글 제목',
          content: writingContentMock,
        }),
      );
    }
    return res(ctx.delay(300), ctx.status(404));
  }),

  // 글 생성(글 업로드): POST
  rest.post('/writings/file', async (_, res, ctx) => {
    return res(ctx.status(201), ctx.set('Location', `/writings/200`));
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
