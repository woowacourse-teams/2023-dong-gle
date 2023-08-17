import { useQuery } from '@tanstack/react-query';
import { getMemberInfo } from 'apis/member';
import Spinner from 'components/@common/Spinner/Spinner';
import ConnectionSection from 'components/ConnectionSection/ConnectionSection';
import Profile from 'components/Profile/Profile';
import { styled } from 'styled-components';
import { MemberResponse } from 'types/apis/member';

const MyPage = () => {
  const { data, isLoading } = useQuery<MemberResponse>(['member'], getMemberInfo);

  return (
    <S.Section>
      <S.Title>마이 페이지</S.Title>
      <S.Container $isLoading={isLoading}>
        {data && !isLoading ? (
          <>
            <Profile name={data.name} />
            <S.ContentContainer>
              <ConnectionSection tistory={data.tistory} medium={data.medium} notion={data.notion} />
            </S.ContentContainer>
          </>
        ) : (
          <Spinner size={60} thickness={8} />
        )}
      </S.Container>
    </S.Section>
  );
};

export default MyPage;

const S = {
  Section: styled.section`
    width: 100vw;
    height: 100vh;
  `,

  Title: styled.h1`
    display: flex;
    align-items: center;
    height: 12rem;
    padding: 0 8rem;

    font-size: 4rem;
  `,

  Container: styled.div<{ $isLoading: boolean }>`
    display: flex;
    justify-content: ${({ $isLoading }) => ($isLoading ? 'center' : 'none')};
    align-items: ${({ $isLoading }) => ($isLoading ? 'center' : 'none')};
    width: 100%;
    height: calc(100% - 12rem);
    border-top: 1px solid ${({ theme }) => theme.color.gray5};
  `,

  ProfileWrapper: styled.div`
    width: 30%;
    height: 100%;
    padding: 4rem 0;
    background-color: ${({ theme }) => theme.color.gray2};
    border-right: 1px solid ${({ theme }) => theme.color.gray5};
  `,

  ContentContainer: styled.div`
    display: flex;
    flex-direction: column;
    gap: 8rem;
    width: 70%;
    padding: 4rem;
    font-size: 1.2rem;
  `,
};
