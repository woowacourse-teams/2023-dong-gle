import { MediumLogoIcon, NotionIcon, TistoryLogoIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { styled } from 'styled-components';
import { MediumConnection, NotionConnection, TistoryConnection } from 'types/apis/member';

type Props = {
  tistory: TistoryConnection;
  medium: MediumConnection;
  notion: NotionConnection;
};

const ConnectionSection = ({ tistory, medium, notion }: Props) => {
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
              <Button size='small'>해제하기</Button>
            ) : (
              <Button variant='secondary' size='small'>
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
              <Button size='small'>해제하기</Button>
            ) : (
              <Button variant='secondary' size='small'>
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
              <Button size='small'>해제하기</Button>
            ) : (
              <Button variant='secondary' size='small'>
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
