import { context } from 'msw';

export const jsonCtx = (json: any, statusCode = 200, delay = 500) => [
  context.delay(delay),
  context.json(json),
  context.status(statusCode),
];
