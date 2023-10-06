import { context } from 'msw';
import { HttpErrorResponseBody } from 'types/apis/error';

export const jsonCtx = <Json>(json: Json, statusCode = 200, delay = 500) => [
  context.delay(delay),
  context.json<Json>(json),
  context.status(statusCode),
];

export const errorCtx = (
  errorJson: HttpErrorResponseBody = {
    error: {
      message: '유저에게 보여줄 에러메시지 입니다.',
      hint: '개발자가 참고할 에러메시지 입니다.',
      code: 400,
    },
  },
  statusCode = 400,
  delay = 500,
) => [
  context.delay(delay),
  context.json<HttpErrorResponseBody>(errorJson),
  context.status(statusCode),
];

export const withoutJson = (statusCode = 200, delay = 500) => [
  context.delay(delay),
  context.status(statusCode),
];
