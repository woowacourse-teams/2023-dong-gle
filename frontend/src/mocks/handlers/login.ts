import { oauthLoginURL, oauthRedirectURL } from 'constants/apis/url';
import { rest } from 'msw';

export const loginHandlers = [
  // 카카오 로그인 페이지로 리다이렉트 요청: GET
  rest.get(oauthRedirectURL('kakao'), (_, res, ctx) => {
    return res(ctx.delay(300), ctx.status(302));
  }),

  // 카카오 로그인/회원가입: POST
  rest.post(oauthLoginURL('kakao'), (_, res, ctx) => {
    return res(ctx.delay(300), ctx.status(200));
  }),
];
