import { memberURL } from 'constants/apis/url';
import { member } from 'mocks/memberContentsMock';
import { rest } from 'msw';

export const memberHandlers = [
  rest.get(memberURL, (_, res, ctx) => {
    return res(ctx.json(member), ctx.status(200));
  }),
];
