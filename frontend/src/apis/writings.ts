import { http } from './fetch';

const baseURL = '';
const writingURL = `${baseURL}/writings`;

// 글 생성(글 업로드): POST
export const addWriting = (file: FormData) =>
  http.post(`${writingURL}/file`, {
    body: file,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });

// 글 조회: GET
export const getWriting = (writingId: number) => http.get(`${writingURL}/${writingId}`);

// 글 정보: GET
export const getWritingProperties = (writingId: number) =>
  http.get(`${writingURL}/${writingId}/properties`);

// 글 발행하기: POST
// TODO: publishTo 타입 블로그 타입으로 변경
export const publishWriting = (writingId: number, publishTo: string) =>
  http.get(`${writingURL}/${writingId}/publish`, {
    body: JSON.stringify({
      publishTo,
    }),
  });
