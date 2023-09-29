import { context } from 'msw';

export const jsonCtx = <Json>(json: Json, statusCode = 200, delay = 500) => [
  context.delay(delay),
  context.json<Json>(json),
  context.status(statusCode),
];

export const withoutJson = (statusCode = 200, delay = 500) => [
  context.delay(delay),
  context.status(statusCode),
];
