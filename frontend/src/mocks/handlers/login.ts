import { loginURL } from 'constants/apis/url';
import { rest } from 'msw';

export const loginHandlers = [
  // 카카오 로그인/회원가입: POST
  rest.post(`${loginURL}/kakao`, (_, res, ctx) => {
    return res(ctx.delay(300), ctx.status(200));
  }),
];
