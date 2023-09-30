import { trashURL } from 'constants/apis/url';
import { ERROR_RESPONSE, isValidAccessToken } from 'mocks/auth';
import { rest } from 'msw';
import { jsonCtx, withoutJson } from './utils';
import {
  deleteWritingFromTrashTable,
  moveWritingToTrashTable,
  restoreWritingFromTrashTable,
  trashcanWritingTable,
} from 'mocks/data/trashCanPage';
import { addRestoredWritings, deleteWritingsIn사이드바카테고리 } from 'mocks/data/category';
import { DeleteWritingRequest } from 'types/apis/trash';

export const trashHandlers = [
  // 글 휴지통으로 이동 / 글 영구 삭제
  rest.post(trashURL, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    const { writingIds, isPermanentDelete } = await req.json<DeleteWritingRequest>();

    if (isPermanentDelete) {
      deleteWritingFromTrashTable(writingIds);
    } else {
      deleteWritingsIn사이드바카테고리(writingIds);
      moveWritingToTrashTable();
    }

    return res(...withoutJson());
  }),

  // 휴지통에서 글 목록 조회
  rest.get(trashURL, (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    return res(...jsonCtx(trashcanWritingTable));
  }),

  // 휴지통에서 글 복구
  rest.post(`${trashURL}/restore`, async (req, res, ctx) => {
    if (!isValidAccessToken(req)) return res(ctx.status(401), ctx.json(ERROR_RESPONSE));

    const { writingIds } = await req.json();

    restoreWritingFromTrashTable(writingIds);
    addRestoredWritings(writingIds);

    return res(...withoutJson());
  }),
];
