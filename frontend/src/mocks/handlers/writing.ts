import { rest } from 'msw';
import { writingURL } from 'constants/apis/url';
import { writingContentMock } from 'mocks/writingContentMock';
import {
  GetCategoryIdWritingListResponse,
  GetWritingPropertiesResponse,
  GetWritingResponse,
} from 'types/apis/writings';
import { getWritingTableMock } from 'mocks/writingTableMock';

export const writingHandlers = [
  // 글 조회: GET
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

  // 글 정보: GET
  rest.get(`${writingURL}/:writingId/properties`, (req, res, ctx) => {
    const writingId = Number(req.params.writingId);

    if (writingId === 200) {
      return res(
        ctx.delay(300),
        ctx.status(200),
        ctx.json<GetWritingPropertiesResponse>({
          createdAt: new Date('2023-07-11T06:55:46.922Z'),
          publishedDetails: [
            {
              blogName: 'MEDIUM',
              publishedAt: new Date('2023-07-11T06:55:46.922Z'),
              tags: ['개발', '네트워크', '서버'],
            },
            {
              blogName: 'TISTORY',
              publishedAt: new Date('2023-06-11T06:55:46.922Z'),
              tags: ['프로그래밍', 'CS'],
            },
          ],
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

  // 카테고리 글 상세 목록 조회: GET
  rest.get(`${writingURL}`, (req, res, ctx) => {
    const categoryId = Number(req.url.searchParams.get('categoryId'));

    return res(
      ctx.json<GetCategoryIdWritingListResponse>(getWritingTableMock(categoryId)),
      // ctx.delay(1000),
      ctx.status(200),
    );
  }),
];
