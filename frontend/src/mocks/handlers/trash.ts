import { trashURL } from 'constants/apis/url';
import { rest } from 'msw';

export const trashHandlers = [
  // 글 휴지통으로 이동 / 글 영구 삭제
  rest.post(trashURL, (_, res, ctx) => {
    return res(ctx.status(200));
  }),
];
