import { rest } from 'msw';

export const errorHandlers = [
  rest.get('/error/unAuthorized', (req, res, ctx) => {
    return res(ctx.status(401));
  }),
];
