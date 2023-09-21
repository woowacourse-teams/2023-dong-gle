import { memberURL } from 'constants/apis/url';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { member } from 'mocks/memberContentsMock';
import { rest } from 'msw';

export const memberHandlers = [
  // 멤버 정보 가져오기: GET
  rest.get(memberURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(ctx.json(member), ctx.status(200));
  }),

  // 회원 탈퇴: POST
  rest.post(`${memberURL}/delete`, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(ctx.status(200));
  }),
];
