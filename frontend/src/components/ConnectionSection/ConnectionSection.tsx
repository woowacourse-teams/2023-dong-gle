import { MediumLogoIcon, NotionIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Input from 'components/@common/Input/Input';
import { ConnectionPlatforms } from 'constants/components/myPage';
import useUncontrolledInput from 'hooks/@common/useUncontrolledInput';
import { css, styled } from 'styled-components';
import { MediumConnection, NotionConnection, TistoryConnection } from 'types/apis/member';
import { KeyboardEventHandler } from 'react';
import { useConnect } from './useConnect';
import { MAX_WIDTH } from 'constants/style';

type Props = {
  medium: MediumConnection;
  notion: NotionConnection;
};

const ConnectionSection = ({ medium, notion }: Props) => {
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
              <MediumLogoIcon width={40} height={40} />
              <S.PlatformTitle>미디엄</S.PlatformTitle>
            </S.IconContainer>
            {medium.isConnected ? (
              <Button
                size='small'
                onClick={() => disconnect(ConnectionPlatforms.medium)}
                aria-label='미디엄 연결 해제하기'
              >
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
                aria-label='미디엄 토큰 입력 창'
              />
            ) : (
              <Button
                variant='secondary'
                size='small'
                onClick={openInput}
                aria-label='미디엄 연결하기'
              >
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
              <Button
                size='small'
                onClick={() => disconnect(ConnectionPlatforms.notion)}
                aria-label='노션 연결 해제하기'
              >
                해제하기
              </Button>
            ) : (
              <Button
                variant='secondary'
                size='small'
                onClick={() => redirect(ConnectionPlatforms.notion)}
                aria-label='노션 연결하기'
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

const generateResponsiveStyle = {
  connectionContainerButton: css`
    @media (max-width: ${MAX_WIDTH.mobileSmall}) {
      p {
        width: 20px;
        height: 20px;
        font-size: 1rem;
        border-radius: 50%;
      }
    }
  `,

  connectionItem: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      gap: 4rem;
    }
  `,

  platformTitle: css`
    @media (max-width: ${MAX_WIDTH.mobileLarge}) {
      font-size: 1.6rem;
    }
  `,
};

const S = {
  ConnectionContainer: styled.div`
    display: flex;
    flex-direction: column;
    gap: 2rem;

    button {
      ${generateResponsiveStyle.connectionContainerButton}
    }
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

    ${generateResponsiveStyle.connectionItem}
  `,

  IconContainer: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    font-size: 1.6rem;
  `,

  PlatformTitle: styled.h3`
    ${generateResponsiveStyle.platformTitle}
  `,

  AlreadyConnection: styled.p``,
};
