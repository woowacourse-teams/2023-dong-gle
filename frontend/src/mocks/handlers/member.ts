import { memberURL } from 'constants/apis/url';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { member } from 'mocks/data/member';
import { rest } from 'msw';
import { jsonCtx, withoutJson } from './utils';

export const memberHandlers = [
  // 멤버 정보 가져오기: GET
  rest.get(memberURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(...jsonCtx(member));
  }),

  // 회원 탈퇴: POST
  rest.post(`${memberURL}/delete`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(...errorCtx(ERROR_RESPONSE, 401));

    return res(...withoutJson());
  }),
];
