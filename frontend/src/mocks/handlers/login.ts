import { authURL, loginURL } from 'constants/apis/url';
import { MOCK_ACCESS_TOKEN } from 'mocks/auth';
import { rest } from 'msw';

export const loginHandlers = [
  // 카카오 로그인/회원가입: POST
  rest.post(`${loginURL}/kakao`, (_, res, ctx) => {
    return res(ctx.delay(300), ctx.status(200), ctx.json({ accessToken: MOCK_ACCESS_TOKEN }));
  }),

  rest.post(`${authURL}/logout`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),
];
