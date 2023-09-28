import { memberURL } from 'constants/apis/url';
import { http } from './fetch';
import { MemberResponse } from 'types/apis/member';

// 멤버페이지 정보 조회: GET
export const getMemberInfo = (): Promise<MemberResponse> => http.get(memberURL).json();

// 멤버 탈퇴: POST
export const deleteMemberAccount = () => http.post(`${memberURL}/delete`);
