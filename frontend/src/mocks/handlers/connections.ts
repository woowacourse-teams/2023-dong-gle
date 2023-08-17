import { connectionsURL } from 'constants/apis/url';
import { rest } from 'msw';

export const connectionsHandlers = [
  // 티스토리 정보 저장
  rest.post(`${connectionsURL}/tistory`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 미디움 정보 저장
  rest.post(`${connectionsURL}/medium`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 노션 정보 저장
  rest.post(`${connectionsURL}/notion`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 티스토리 연결 해제
  rest.post(`${connectionsURL}/tistory/disconnect`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 미디움 연결 해제
  rest.post(`${connectionsURL}/medium/disconnect`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 노션 연결 해제
  rest.post(`${connectionsURL}/notion/disconnect`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),
];
