import { useQuery } from '@tanstack/react-query';
import { getMemberInfo } from 'apis/member';
import Button from 'components/@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import ConnectionSection from 'components/ConnectionSection/ConnectionSection';
import Profile from 'components/Profile/Profile';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { MemberResponse } from 'types/apis/member';

const MyPage = () => {
  const { data, isLoading } = useQuery<MemberResponse>(['member'], getMemberInfo);
  const { goSpacePage } = usePageNavigate();

  if (isLoading)
    return (
      <S.SpinnerContainer>
        <Spinner size={48} thickness={6} />
        <p>마이 페이지로 이동 중입니다...</p>
      </S.SpinnerContainer>
    );

  return (
    <S.Section>
      <S.Header>
        <S.Title>마이 페이지</S.Title>
        <Button variant='secondary' size='small' onClick={goSpacePage}>
          스페이스로 가기
        </Button>
      </S.Header>
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

  Header: styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 4rem;
  `,

  Title: styled.h1`
    display: flex;
    align-items: center;
    height: 12rem;
    padding-left: 4rem;
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

  ContentContainer: styled.div`
    display: flex;
    flex-direction: column;
    gap: 8rem;
    width: 70%;
    padding: 4rem;
    font-size: 1.2rem;
  `,

  SpinnerContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1.5rem;
    width: 100vw;
    height: 100vh;
    font-size: 1.5rem;
  `,
};
