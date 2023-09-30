import { trashURL } from 'constants/apis/url';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { rest } from 'msw';
import { jsonCtx, withoutJson } from './utils';
import { addDeletedWriting, trashcanWritingTable } from 'mocks/data/trashCanPage';

export const trashHandlers = [
  // 글 휴지통으로 이동 / 글 영구 삭제
  rest.post(trashURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    addDeletedWriting();

    return res(...withoutJson());
  }),

  // 휴지통에서 글 목록 조회
  rest.get(trashURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...jsonCtx(trashcanWritingTable));
  }),

  // 휴지통에서 글 복구
  rest.post(`${trashURL}/restore`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...withoutJson());
  }),
];
