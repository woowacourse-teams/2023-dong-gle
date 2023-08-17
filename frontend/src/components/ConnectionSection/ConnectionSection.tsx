import { MediumLogoIcon, NotionIcon, TistoryLogoIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Input from 'components/@common/Input/Input';
import { ConnectionPlatforms } from 'constants/components/myPage';
import useUncontrolledInput from 'hooks/@common/useUncontrolledInput';
import { styled } from 'styled-components';
import { MediumConnection, NotionConnection, TistoryConnection } from 'types/apis/member';
import { KeyboardEventHandler } from 'react';
import { useConnect } from './useConnect';

type Props = {
  tistory: TistoryConnection;
  medium: MediumConnection;
  notion: NotionConnection;
};

const ConnectionSection = ({ tistory, medium, notion }: Props) => {
  const { inputRef, escapeInput, isInputOpen, openInput, resetInput, isError, setIsError } =
    useUncontrolledInput();
  const { requestStoreMediumInfo, redirect, disconnect } = useConnect();

  const storeMediumInfo: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key !== 'Enter') return;

    const token = e.currentTarget.value;

    if (token.length === 0) {
      setIsError(true);
      return;
    }

    resetInput();
    requestStoreMediumInfo({
      token,
    });
  };

  return (
    <>
      <S.ConnectionContainer>
        <S.ContentContainerTitle>블로그와 연결하기</S.ContentContainerTitle>
        <S.ConnectionList>
          <S.ConnectionItem>
            <S.IconContainer>
              <TistoryLogoIcon width={40} height={40} />
              <S.PlatformTitle>티스토리</S.PlatformTitle>
            </S.IconContainer>
            {tistory.isConnected ? (
              <Button size='small' onClick={() => disconnect(ConnectionPlatforms.tistory)}>
                해제하기
              </Button>
            ) : (
              <Button
                variant='secondary'
                size='small'
                onClick={() => redirect(ConnectionPlatforms.tistory)}
              >
                연결하기
              </Button>
            )}
          </S.ConnectionItem>
          <S.ConnectionItem>
            <S.IconContainer>
              <MediumLogoIcon width={40} height={40} />
              <S.PlatformTitle>미디움</S.PlatformTitle>
            </S.IconContainer>
            {medium.isConnected ? (
              <Button size='small' onClick={() => disconnect(ConnectionPlatforms.medium)}>
                해제하기
              </Button>
            ) : isInputOpen ? (
              <Input
                type='text'
                variant='underlined'
                size='small'
                placeholder='토큰을 입력해주세요'
                ref={inputRef}
                isError={isError}
                onBlur={resetInput}
                onKeyDown={escapeInput}
                onKeyUp={storeMediumInfo}
                aria-label='미디움 토큰 입력 창'
              />
            ) : (
              <Button variant='secondary' size='small' onClick={openInput}>
                연결하기
              </Button>
            )}
          </S.ConnectionItem>
        </S.ConnectionList>
      </S.ConnectionContainer>
      <S.ConnectionContainer>
        <S.ContentContainerTitle>노션과 연결하기</S.ContentContainerTitle>
        <S.ConnectionList>
          <S.ConnectionItem>
            <S.IconContainer>
              <NotionIcon width={32} height={32} />
              <S.PlatformTitle>노션</S.PlatformTitle>
            </S.IconContainer>
            {notion.isConnected ? (
              <Button size='small' onClick={() => disconnect(ConnectionPlatforms.notion)}>
                해제하기
              </Button>
            ) : (
              <Button
                variant='secondary'
                size='small'
                onClick={() => redirect(ConnectionPlatforms.notion)}
              >
                연결하기
              </Button>
            )}
          </S.ConnectionItem>
        </S.ConnectionList>
      </S.ConnectionContainer>
    </>
  );
};

export default ConnectionSection;

const S = {
  ConnectionContainer: styled.div`
    display: flex;
    flex-direction: column;
    gap: 2rem;
  `,

  ContentContainerTitle: styled.h2``,

  ConnectionList: styled.ul`
    display: flex;
    flex-direction: column;
    border: 1px solid ${({ theme }) => theme.color.gray5};
    border-radius: 4px;

    li:not(:last-child) {
      border-bottom: 1px solid ${({ theme }) => theme.color.gray5};
    }
  `,

  ConnectionItem: styled.li`
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 8rem;
    width: 100%;
    padding: 1.2rem;
    background-color: ${({ theme }) => theme.color.gray2};
  `,

  IconContainer: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    font-size: 1.6rem;
  `,

  PlatformTitle: styled.h3``,

  AlreadyConnection: styled.p``,
};
