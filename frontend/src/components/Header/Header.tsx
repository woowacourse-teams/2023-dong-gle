import { SettingIcon, SidebarLeftIcon, SidebarRightIcon } from 'assets/icons';
import InfoMenu from 'components/InfoMenu/InfoMenu';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { HEADER_STYLE } from 'styles/layoutStyle';

type Props = {
  onClickLeftSidebar: () => void;
  onClickRightSidebar: () => void;
  isWritingViewerActive: boolean;
};

const Header = ({ onClickLeftSidebar, onClickRightSidebar, isWritingViewerActive }: Props) => {
  const { goMyPage } = usePageNavigate();

  return (
    <S.Container>
      <S.IconsBox>
        <S.TransparentButton onClick={goMyPage} aria-label='마이 페이지 이동'>
          <SettingIcon width='2.4rem' height='2.4rem' />
        </S.TransparentButton>
        <S.TransparentButton onClick={onClickLeftSidebar} aria-label='왼쪽 사이드바 토글'>
          <SidebarLeftIcon width='2.4rem' height='2.4rem' />
        </S.TransparentButton>
      </S.IconsBox>
      <S.IconsBox>
        <InfoMenu />
        {/* <Button size='small' variant='text' onClick={() => logout.mutate()}>
          로그아웃
        </Button> */}
        {isWritingViewerActive && (
          <S.TransparentButton onClick={onClickRightSidebar} aria-label='오른쪽 사이드바 토글'>
            <SidebarRightIcon width='2.4rem' height='2.4rem' />
          </S.TransparentButton>
        )}
      </S.IconsBox>
    </S.Container>
  );
};

export default Header;

const S = {
  Container: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 1rem;
    height: ${HEADER_STYLE.height};
  `,

  IconsBox: styled.div`
    display: flex;
    gap: 0.8rem;
  `,

  TransparentButton: styled.button`
    padding: 0.8rem 0.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray4};
    }
  `,
};
