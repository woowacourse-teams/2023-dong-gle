import { trashURL } from 'constants/apis/url';
import { deletedWritings } from 'mocks/trashCanContentsMock';
import { rest } from 'msw';

export const trashHandlers = [
  // 글 휴지통으로 이동 / 글 영구 삭제
  rest.post(trashURL, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 휴지통에서 글 목록 조회
  rest.get(trashURL, (_, res, ctx) => {
    return res(ctx.json(deletedWritings), ctx.status(200));
  }),

  // 휴지통에서 글 복구
  rest.post(`${trashURL}/restore`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),
];
