import { memberURL } from 'constants/apis/url';
import { http } from './fetch';
import { MediumInfo, NotionInfo, TistoryInfo } from 'types/apis/connections';

// 티스토리 정보 저장: POST
export const storeTistoryInfo = (body: TistoryInfo) =>
  http.post(`${memberURL}/tistory`, { json: body });

// 미디움 정보 저장: POST
export const storeMediumInfo = (body: MediumInfo) =>
  http.post(`${memberURL}/medium`, { json: body });

// 노션 정보 저장: POST
export const storeNotionInfo = (body: NotionInfo) =>
  http.post(`${memberURL}/notion`, { json: body });
