import type { AddWritingRequest, PublishWritingRequest } from 'types/apis/writings';
import { http } from './fetch';

const baseURL = '';
const writingURL = `${baseURL}/writings`;

// 글 생성(글 업로드): POST
export const addWriting = (body: AddWritingRequest) =>
  http.post(`${writingURL}/file`, {
    body: JSON.stringify(body),
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
export const publishWriting = (writingId: number, body: PublishWritingRequest) =>
  http.get(`${writingURL}/${writingId}/publish`, {
    body: JSON.stringify(body),
  });
