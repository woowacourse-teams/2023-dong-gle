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
import { errorCtx, jsonCtx, withoutJson } from './utils';
import { renameWritingTitle, writing, writingProperties } from 'mocks/data/writingPage';
import { writingTable } from 'mocks/data/writingTablePage';
import { isConnected } from 'mocks/data/member';

export const writingHandlers = [
  // 전체 글 조회: GET
  rest.get(`${writingURL}/home`, (_, res, ctx) => {
    return res(...jsonCtx(homepageWritingTable));
  }),

  // 글 조회: GET
  rest.get(`${writingURL}/:writingId`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(...jsonCtx<GetWritingResponse>(writing));
  }),

  // 글 정보: GET
  rest.get(`${writingURL}/:writingId/properties`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(...jsonCtx<GetWritingPropertiesResponse>(writingProperties));
  }),

  // 글 생성(글 업로드): POST
  rest.post(`${writingURL}/file`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(ctx.delay(1000), ctx.status(201), ctx.set('Location', `/writings/1`));
  }),

  // 글 생성(글 업로드): POST
  rest.post(`${writingURL}/notion`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(ctx.delay(1000), ctx.status(201), ctx.set('Location', `/writings/1`));
  }),

  // 글 티스토리 블로그로 발행: POST
  rest.post(`${writingURL}/:writingId/publish/tistory`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return isConnected('tistory') ? res(...withoutJson()) : res(...errorCtx());
  }),

  // 글 미디엄 블로그로 발행: POST
  rest.post(`${writingURL}/:writingId/publish/medium`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return isConnected('medium') ? res(...withoutJson()) : res(...errorCtx());
  }),

  // 카테고리 글 상세 목록 조회: GET
  rest.get(`${writingURL}`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(...jsonCtx<GetDetailWritingsResponse>(writingTable));
  }),

  // 글 수정(이름, 순서): PATCH
  rest.patch(`${writingURL}/:writingId`, async (req, res, ctx) => {
    const writingId = Number(req.params.writingId);
    const body = await req.json();

    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    // 글 이름 수정
    if (hasDefinedField<UpdateWritingTitleArgs['body']>(body, 'title')) {
      if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

      renameWritingTitle(body.title);
    }

    return res(...withoutJson());
  }),
];
