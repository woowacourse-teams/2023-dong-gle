import { errorCtx } from './../../src/mocks/handlers/utils';
import { ERROR_RESPONSE } from 'mocks/auth';
import { writingURL } from '../constants/url';

// 글 수정(이름, 순서): PATCH
// 글 이름 수정
export const overrideWritingTitleWithError = () => {
  cy.window().then((window) => {
    const { worker, rest } = window.msw;

    worker.use(
      rest.patch(`${writingURL}/:writingId`, async (req, res, ctx) => {
        const body = await req.json();

        if ('title' in body) {
          return res(...errorCtx());
        }
      }),
    );
  });
};
