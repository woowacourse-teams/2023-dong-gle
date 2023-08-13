import { trashURL } from 'constants/apis/url';
import { http } from './fetch';

// 글 휴지통으로 이동: POST
export const moveToTrash = (writingIds: number[]) =>
  http.post(`${trashURL}`, {
    json: {
      writingIds,
      isPermanentDelete: false,
    },
  });

// 글 영구 삭제: POST
export const deletePermanentWritings = (writingIds: number[]) =>
  http.post(`${trashURL}`, {
    json: {
      writingIds,
      isPermanentDelete: true,
    },
  });
