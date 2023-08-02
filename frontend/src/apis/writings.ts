import { writingURL } from 'constants/apis/url';
import { http } from './fetch';
import type {
  AddNotionWritingRequest,
  AddWritingRequest,
  PublishWritingArgs,
} from 'types/apis/writings';

// 글 생성(글 업로드): POST
export const addWriting = (body: AddWritingRequest) =>
  http.post(`${writingURL}/file`, {
    body,
    // headers: {
    //   'Content-Type': 'multipart/form-data',
    // },
  });

// 노션 - 글 생성(글 업로드): POST
export const addNotionWriting = (body: AddNotionWritingRequest) =>
  http.post(`${writingURL}/notion`, {
    body: JSON.stringify(body),
    headers: {
      'Content-Type': 'application/json',
    },
  });

// 글 조회: GET
export const getWriting = (writingId: number) => http.get(`${writingURL}/${writingId}`);

// 글 정보: GET
export const getWritingProperties = (writingId: number) =>
  http.get(`${writingURL}/${writingId}/properties`);

// 글 발행하기: POST
export const publishWriting = ({ writingId, body }: PublishWritingArgs) =>
  http.post(`${writingURL}/${writingId}/publish`, { json: body });

// 카테고리 글 상세 목록 조회 : GET
export const getCategoryIdWritingList = (categoryId: number) =>
  http.get(`${writingURL}?categoryId=${categoryId}`);
