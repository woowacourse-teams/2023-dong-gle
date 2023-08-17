import { useQuery } from '@tanstack/react-query';
import { getMemberInfo } from 'apis/member';
import { MemberResponse } from 'types/apis/member';

export const useMember = () => {
  const { data, isLoading } = useQuery<MemberResponse>(['member'], getMemberInfo);
  const id = data?.id;
  const name = data?.name;
  const tistory = data?.tistory;
  const notion = data?.notion;
  const medium = data?.medium;

  return { isLoading, id, name, tistory, notion, medium };
};
