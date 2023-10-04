import { authURL, loginURL } from 'constants/apis/url';
import { MOCK_ACCESS_TOKEN } from 'mocks/auth';
import { rest } from 'msw';
import { jsonCtx, withoutJson } from './utils';

export const loginHandlers = [
  // 카카오 로그인/회원가입: POST
  rest.post(`${loginURL}/kakao`, (_, res, ctx) => {
    return res(...jsonCtx({ accessToken: MOCK_ACCESS_TOKEN }));
  }),

  rest.post(`${authURL}/logout`, (_, res, ctx) => {
    return res(...withoutJson());
  }),
];
