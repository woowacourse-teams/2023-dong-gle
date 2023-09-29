import { authURL } from 'constants/apis/url';
import { MOCK_ACCESS_TOKEN } from 'mocks/auth';
import { rest } from 'msw';

export const authHandlers = [
  rest.post(`${authURL}/token/refresh`, (req, res, ctx) => {
    return res(
      ctx.delay(3000),
      ctx.status(200),
      ctx.json({
        accessToken: MOCK_ACCESS_TOKEN,
      }),
    );
  }),
];
