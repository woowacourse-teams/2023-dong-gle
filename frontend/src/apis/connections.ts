import { connectionsURL } from 'constants/apis/url';
import { http } from './fetch';
import { MediumInfo, NotionInfo, TistoryInfo } from 'types/apis/connections';
import { ConnectionPlatforms } from 'constants/components/myPage';

// 티스토리 정보 저장: POST
export const storeTistoryInfo = (body: TistoryInfo) =>
  http.post(`${connectionsURL}/tistory`, { json: body });

// 미디움 정보 저장: POST
export const storeMediumInfo = (body: MediumInfo) =>
  http.post(`${connectionsURL}/medium`, { json: body });

// 노션 정보 저장: POST
export const storeNotionInfo = (body: NotionInfo) =>
  http.post(`${connectionsURL}/notion`, { json: body });

// 연결 해제: POST
export const disconnect = (platform: ConnectionPlatforms) =>
  http.post(`${connectionsURL}/${platform}/disconnect`);
