import { trashURL } from 'constants/apis/url';
import { http } from './fetch';
import { DeleteWritingRequest, GetDeletedWritingsResponse } from 'types/apis/trash';

// 글 휴지통으로 이동: POST
export const moveToTrash = (writingIds: number[]) => {
  const json: DeleteWritingRequest = {
    writingIds,
    isPermanentDelete: false,
  };
  return http.post(trashURL, { json });
};

// 글 영구 삭제: POST
export const deletePermanentWritings = (writingIds: number[]) => {
  const json: DeleteWritingRequest = {
    writingIds,
    isPermanentDelete: true,
  };
  return http.post(trashURL, { json });
};

// 휴지통에 있는 글 목록 조회: GET
export const getDeletedWritings = (): Promise<GetDeletedWritingsResponse> =>
  http.get(trashURL).json();

// 휴지통에서 글 복구: POST
export const restoreDeletedWritings = (writingIds: number[]) =>
  http.post(`${trashURL}/restore`, { json: { writingIds } });
