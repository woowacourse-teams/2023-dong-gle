import { useQuery } from '@tanstack/react-query';
import { getMemberInfo } from 'apis/member';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { MemberResponse } from 'types/apis/member';

export const useIsNotionConnected = () => {
  const { data } = useQuery<MemberResponse>(['member'], getMemberInfo);
  const { goMyPage } = usePageNavigate();

  return { isConnected: data?.notion.isConnected, goMyPage };
};
