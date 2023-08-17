import { memberURL } from 'constants/apis/url';
import { http } from './fetch';

// 멤버페이지 정보 조회: GET
export const getMemberInfo = () => http.get(memberURL);
