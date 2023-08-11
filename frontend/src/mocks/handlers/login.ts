import { getRedirectURL } from 'constants/apis/oauth';
import { getOauthURL } from 'constants/apis/url';
import { rest } from 'msw';

export const loginHandlers = [
  // 카카오 로그인 페이지로 리다이렉트 요청: GET
  rest.get(`${getOauthURL('kakao')}?redirect_uri=${getRedirectURL('kakao')}`, (_, res, ctx) => {
    return res(ctx.delay(300), ctx.status(302));
  }),

  // 카카오 로그인/회원가입: POST
  rest.post(getOauthURL('kakao'), (_, res, ctx) => {
    return res(ctx.delay(300), ctx.status(200));
  }),
];
