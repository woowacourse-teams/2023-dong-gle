import { connectionsURL } from 'constants/apis/url';
import { rest } from 'msw';

export const connectionsHandlers = [
  // 티스토리 정보 저장
  rest.post(`${connectionsURL}/tistory`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 노션 정보 저장
  rest.post(`${connectionsURL}/notion`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  // 미디움 토큰 저장
  rest.post(`${connectionsURL}/medium`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),
];
