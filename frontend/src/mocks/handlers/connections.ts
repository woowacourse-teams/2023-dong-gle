import { connectionsURL } from 'constants/apis/url';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { connect, disconnect } from 'mocks/data/member';
import { rest } from 'msw';
import { withoutJson } from './utils';

export const connectionsHandlers = [
  // 티스토리 정보 저장
  rest.post(`${connectionsURL}/tistory`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    connect('tistory');

    return res(...withoutJson());
  }),

  // 미디움 정보 저장
  rest.post(`${connectionsURL}/medium`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    connect('medium');

    return res(...withoutJson());
  }),

  // 노션 정보 저장
  rest.post(`${connectionsURL}/notion`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    connect('notion');

    return res(...withoutJson());
  }),

  // 티스토리 연결 해제
  rest.post(`${connectionsURL}/tistory/disconnect`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    disconnect('tistory');

    return res(...withoutJson());
  }),

  // 미디움 연결 해제
  rest.post(`${connectionsURL}/medium/disconnect`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    disconnect('medium');

    return res(...withoutJson());
  }),

  // 노션 연결 해제
  rest.post(`${connectionsURL}/notion/disconnect`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    disconnect('notion');

    return res(...withoutJson());
  }),
];
