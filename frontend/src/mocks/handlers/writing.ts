import { rest } from 'msw';
import { writingURL } from 'constants/apis/url';
import { writingContentMock } from 'mocks/writingContentMock';
import {
  GetDetailWritingsResponse,
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
          title: '동글을 소개합니다 🎉',
          content: writingContentMock,
        }),
      );
    }
    return res(ctx.delay(300), ctx.status(404), ctx.json({ message: '글을 찾을 수 없습니다.' }));
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
              publishedUrl: 'https://medium.com/',
            },
            {
              blogName: 'TISTORY',
              publishedAt: new Date('2023-06-11T06:55:46.922Z'),
              tags: ['프로그래밍', 'CS'],
              publishedUrl: 'https://www.tistory.com/',
            },
          ],
        }),
      );
    }
    return res(
      ctx.delay(300),
      ctx.status(404),
      ctx.json({ message: '글 정보를 찾을 수 없습니다.' }),
    );
  }),

  // 글 생성(글 업로드): POST
  rest.post(`${writingURL}/file`, async (_, res, ctx) => {
    return res(ctx.delay(3000), ctx.status(201), ctx.set('Location', `/writings/200`));
  }),

  // 글 생성(글 업로드): POST
  rest.post(`${writingURL}/notion`, async (_, res, ctx) => {
    // return res(ctx.delay(1000), ctx.status(201), ctx.set('Location', `/writings/200`));
    return res(
      ctx.delay(1000),
      ctx.status(404),
      ctx.json({ message: '유효한 노션 id를 입력해주세요.' }),
    );
  }),

  // 글 블로그로 발행: POST
  rest.post(`${writingURL}/:writingId/publish`, async (req, res, ctx) => {
    const blog = ['MEDIUM', 'TISTORY'];
    const id = Number(req.params.writingId);
    const { publishTo } = await req.json();

    if (!blog.includes(publishTo) || typeof id !== 'number')
      return res(
        ctx.status(404),
        ctx.json({
          message: '글 발행을 실패했습니다.',
        }),
      );

    return res(ctx.delay(3000), ctx.status(200));
  }),

  // 카테고리 글 상세 목록 조회: GET
  rest.get(`${writingURL}`, (req, res, ctx) => {
    const categoryId = Number(req.url.searchParams.get('categoryId'));

    return res(
      ctx.json<GetDetailWritingsResponse>(getWritingTableMock(categoryId)),
      // ctx.delay(1000),
      ctx.status(200),
    );
  }),

  // 글 제목 변경: PATCH
  rest.patch(`${writingURL}/:writingId`, async (req, res, ctx) => {
    return res(ctx.delay(3000), ctx.status(200));
  }),
];
