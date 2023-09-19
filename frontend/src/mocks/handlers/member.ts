import { memberURL } from 'constants/apis/url';
import { member } from 'mocks/memberContentsMock';
import { rest } from 'msw';

export const memberHandlers = [
  rest.get(memberURL, (_, res, ctx) => {
    return res(ctx.json(member), ctx.status(200));
  }),

  // 회원 탈퇴: POST
  rest.post(`${memberURL}/delete`, (_, res, ctx) => {
    return res(ctx.status(200));
  }),
];
