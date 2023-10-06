import { writingURL } from 'constants/apis/url';
import { http } from './fetch';
import type {
  AddNotionWritingRequest,
  AddWritingRequest,
  GetDetailWritingsResponse,
  GetWritingPropertiesResponse,
  GetWritingResponse,
  UpdateWritingTitleArgs,
  UpdateWritingOrderArgs,
  GetHomeWritingsResponse,
  PublishWritingToTistoryArgs,
  PublishWritingToMediumArgs,
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
export const getWriting = (writingId: number): Promise<GetWritingResponse> =>
  http.get(`${writingURL}/${writingId}`).json();

// 글 정보: GET
export const getWritingProperties = (writingId: number): Promise<GetWritingPropertiesResponse> =>
  http.get(`${writingURL}/${writingId}/properties`).json();

// 티스토리 글 발행: POST
export const publishWritingToTistory = ({ writingId, body }: PublishWritingToTistoryArgs) =>
  http.post(`${writingURL}/${writingId}/publish/tistory`, { json: body });

// 미디엄 글 발행: POST
export const publishWritingToMedium = ({ writingId, body }: PublishWritingToMediumArgs) =>
  http.post(`${writingURL}/${writingId}/publish/medium`, { json: body });

// 카테고리 글 상세 목록 조회 : GET
export const getDetailWritings = (categoryId: number): Promise<GetDetailWritingsResponse> =>
  http.get(`${writingURL}?categoryId=${categoryId}`).json();

// 글 제목 변경: PATCH
export const updateWritingTitle = ({ writingId, body }: UpdateWritingTitleArgs) =>
  http.patch(`${writingURL}/${writingId}`, { json: body });

// 글 제목 순서 변경: PATCH
export const updateWritingOrder = ({ writingId, body }: UpdateWritingOrderArgs) =>
  http.patch(`${writingURL}/${writingId}`, { json: body });

// 전체 글: GET
export const getHomeWritings = (option: string): Promise<GetHomeWritingsResponse> =>
  http.get(`${writingURL}/home${option}`).json();
