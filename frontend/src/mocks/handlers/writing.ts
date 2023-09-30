import { rest } from 'msw';
import { writingURL } from 'constants/apis/url';
import {
  GetDetailWritingsResponse,
  GetHomeWritingsResponse,
  GetWritingPropertiesResponse,
  GetWritingResponse,
  UpdateWritingOrderArgs,
  UpdateWritingTitleArgs,
} from 'types/apis/writings';
import { hasDefinedField } from 'utils/typeGuard';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { homepageWritingTable } from 'mocks/data/homePage';
import { jsonCtx, withoutJson } from './utils';
import { writing, writingProperties } from 'mocks/data/writingPage';
import { writingTable } from 'mocks/data/writingTablePage';

export const writingHandlers = [
  // 전체 글 조회: GET
  rest.get(`${writingURL}/home`, (_, res, ctx) => {
    return res(...jsonCtx(homepageWritingTable));
  }),

  // 글 조회: GET
  rest.get(`${writingURL}/:writingId`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...jsonCtx<GetWritingResponse>(writing));
  }),

  // 글 정보: GET
  rest.get(`${writingURL}/:writingId/properties`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...jsonCtx<GetWritingPropertiesResponse>(writingProperties));
  }),

  // 글 생성(글 업로드): POST
  rest.post(`${writingURL}/file`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(ctx.delay(3000), ctx.status(201), ctx.set('Location', `/writings/200`));
  }),

  // 글 생성(글 업로드): POST
  rest.post(`${writingURL}/notion`, async (req, res, ctx) => {
    // return res(ctx.delay(1000), ctx.status(201), ctx.set('Location', `/writings/200`));

    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(
      ctx.delay(1000),
      ctx.status(404),
      ctx.json({ message: '유효한 노션 id를 입력해주세요.' }),
    );
  }),

  // 글 티스토리 블로그로 발행: POST
  rest.post(`${writingURL}/:writingId/publish/tistory`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...withoutJson());
  }),

  // 글 미디엄 블로그로 발행: POST
  rest.post(`${writingURL}/:writingId/publish/medium`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...withoutJson());
  }),

  // 카테고리 글 상세 목록 조회: GET
  rest.get(`${writingURL}`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...jsonCtx<GetDetailWritingsResponse>(writingTable));
  }),

  // 글 수정(이름, 순서): PATCH
  rest.patch(`${writingURL}/:writingId`, async (req, res, ctx) => {
    const writingId = Number(req.params.writingId);
    const body = await req.json();

    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    // 글 순서 수정
    if (hasDefinedField<UpdateWritingOrderArgs['body']>(body, 'nextWritingId')) {
      if (!body.nextWritingId) {
        return res(
          ctx.delay(300),
          ctx.status(404),
          ctx.json({
            message: '글 순서 수정 에러',
          }),
        );
      }
    }

    // 글 이름 수정
    if (hasDefinedField<UpdateWritingTitleArgs['body']>(body, 'title')) {
      if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

      if (!body.title)
        return res(
          ctx.delay(300),
          ctx.status(404),
          ctx.json({
            message: '글 이름 수정 에러',
          }),
        );
    }

    return res(ctx.delay(3000), ctx.status(200));
  }),
];
