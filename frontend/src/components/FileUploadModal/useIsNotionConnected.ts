import { useQuery } from '@tanstack/react-query';
import { getMemberInfo } from 'apis/member';
import { MemberResponse } from 'types/apis/member';

export const useIsNotionConnected = () => {
  const { data } = useQuery<MemberResponse>(['member'], getMemberInfo);

  return data?.notion.isConnected;
};
