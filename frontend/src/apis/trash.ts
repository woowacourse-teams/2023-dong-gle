import { trashURL } from 'constants/apis/url';
import { http } from './fetch';
import { GetDeletedWritingsResponse } from 'types/apis/trash';

// 글 휴지통으로 이동: POST
export const moveToTrash = (writingIds: number[]) =>
  http.post(trashURL, {
    json: {
      writingIds,
      isPermanentDelete: false,
    },
  });

// 글 영구 삭제: POST
export const deletePermanentWritings = (writingIds: number[]) =>
  http.post(trashURL, {
    json: {
      writingIds,
      isPermanentDelete: true,
    },
  });

// 휴지통에 있는 글 목록 조회: GET
export const getDeletedWritings = (): Promise<GetDeletedWritingsResponse> => http.get(trashURL);

// 휴지통에서 글 복구: POST
export const restoreDeletedWritings = (writingIds: number[]) =>
  http.post(`${trashURL}/restore`, { json: { writingIds } });
