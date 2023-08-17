import { useMutation } from '@tanstack/react-query';
import { logout as logoutAPI } from 'apis/login';
import { SettingIcon, SidebarLeftIcon, SidebarRightIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { HEADER_STYLE } from 'styles/layoutStyle';

type Props = {
  onClickLeftSidebar: () => void;
  onClickRightSidebar: () => void;
  isWritingViewerActive: boolean;
};

const Header = ({ onClickLeftSidebar, onClickRightSidebar, isWritingViewerActive }: Props) => {
  const toast = useToast();
  const { goHomePage } = usePageNavigate();
  const logout = useMutation(logoutAPI, {
    onSuccess: () => {
      localStorage.removeItem('accessToken');
      goHomePage();
      toast.show({ type: 'success', message: '로그아웃이 완료되었습니다.' });
    },
    onError: () => {
      toast.show({ type: 'error', message: '로그아웃에 실패했습니다.' });
    },
  });

  return (
    <S.Container>
      <S.LeftIconsBox>
        <SettingIcon width='2.4rem' height='2.4rem' />
        <button onClick={onClickLeftSidebar} aria-label='왼쪽 사이드바 토글'>
          <SidebarLeftIcon width='2.4rem' height='2.4rem' />
        </button>
      </S.LeftIconsBox>
      <S.RightIconsBox>
        <Button size='small' variant='text' onClick={() => logout.mutate()}>
          로그아웃
        </Button>
        {isWritingViewerActive && (
          <button onClick={onClickRightSidebar} aria-label='오른쪽 사이드바 토글'>
            <SidebarRightIcon width='2.4rem' height='2.4rem' />
          </button>
        )}
      </S.RightIconsBox>
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

  LeftIconsBox: styled.div`
    display: flex;
    gap: 0.8rem;
  `,

  RightIconsBox: styled.div`
    display: flex;
    gap: 0.8rem;
  `,
};
