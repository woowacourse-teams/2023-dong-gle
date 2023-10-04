import { authURL } from 'constants/apis/url';
import { MOCK_ACCESS_TOKEN } from 'mocks/auth';
import { rest } from 'msw';
import { jsonCtx } from './utils';

export const authHandlers = [
  rest.post(`${authURL}/token/refresh`, (req, res, ctx) => {
    return res(
      ...jsonCtx({
        accessToken: MOCK_ACCESS_TOKEN,
      }),
    );
  }),
];
