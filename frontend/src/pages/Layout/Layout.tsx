import { useEffect, useState } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { styled } from 'styled-components';
import { HEADER_STYLE, LAYOUT_STYLE, sidebarStyle } from 'styles/layoutStyle';
import Header from 'components/Header/Header';
import WritingSideBar from 'components/WritingSideBar/WritingSideBar';
import HelpMenu from 'components/HelpMenu/HelpMenu';
import { useGlobalState, useGlobalStateValue } from '@yogjin/react-global-state';
import {
  activeWritingInfoState,
  leftDrawerState,
  mediaQueryMobileState,
  rightDrawerState,
} from 'globalState';
import LeftSideBar from 'components/LeftSideBar/LeftSideBar';
import { Drawer } from '@donggle/layout-component';

const Layout = () => {
  const isMobile = useGlobalStateValue(mediaQueryMobileState);
  const [isLeftSidebarOpen, setIsLeftSidebarOpen] = useState(isMobile ? false : true);
  const [isRightSidebarOpen, setIsRightSidebarOpen] = useState(true);
  const activeWritingInfo = useGlobalStateValue(activeWritingInfoState);
  const [isLeftDrawerOpen, setIsLeftDrawerOpen] = useGlobalState(leftDrawerState);
  const [isRightDrawerOpen, setIsRightDrawerOpen] = useGlobalState(rightDrawerState);
  const location = useLocation();

  const isWritingViewerActive = activeWritingInfo !== null;

  const toggleLeftSidebar = () => {
    isMobile ? setIsLeftDrawerOpen(true) : setIsLeftSidebarOpen(!isLeftSidebarOpen);
  };

  const toggleRightSidebar = () => {
    isMobile ? setIsRightDrawerOpen(true) : setIsRightSidebarOpen(!isRightSidebarOpen);
  };

  // 모바일 환경이 아닐 때 사이드바를 연 상태로 변경하기 위함.
  useEffect(() => {
    if (!isMobile) setIsLeftSidebarOpen(true);
  }, [isMobile]);

  // 모바일 환경에서 왼쪽 사이드바로 페이지 이동 시(location.pathname 변경 시) 사이드바를 닫기 위함.
  useEffect(() => {
    setIsLeftDrawerOpen(false);
  }, [location.pathname]);

  return (
    <S.Container>
      <Header
        onClickLeftSidebar={toggleLeftSidebar}
        onClickRightSidebar={toggleRightSidebar}
        isWritingViewerActive={isWritingViewerActive}
      />
      <S.Row>
        {isMobile ? (
          <Drawer
            anchor='left'
            size='30rem'
            open={isLeftDrawerOpen}
            onClose={() => setIsLeftDrawerOpen(false)}
          >
            <S.LeftSidebarSection $isLeftSidebarOpen={true}>
              <LeftSideBar />
            </S.LeftSidebarSection>
          </Drawer>
        ) : (
          <S.LeftSidebarSection $isLeftSidebarOpen={isLeftSidebarOpen}>
            <LeftSideBar />
          </S.LeftSidebarSection>
        )}
        <S.Main>
          <Outlet />
          <HelpMenu />
        </S.Main>
        {isWritingViewerActive && isMobile && (
          <Drawer
            anchor='right'
            size='30rem'
            open={isRightDrawerOpen}
            onClose={() => setIsRightDrawerOpen(false)}
          >
            <S.RightSidebarSection $isRightSidebarOpen={true}>
              <WritingSideBar isPublishingSectionActive={!activeWritingInfo?.isDeleted} />
            </S.RightSidebarSection>
          </Drawer>
        )}
        {isWritingViewerActive && !isMobile && (
          <S.RightSidebarSection $isRightSidebarOpen={isRightSidebarOpen}>
            <WritingSideBar isPublishingSectionActive={!activeWritingInfo?.isDeleted} />
          </S.RightSidebarSection>
        )}
      </S.Row>
    </S.Container>
  );
};

export default Layout;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    padding: ${LAYOUT_STYLE.padding};
    background-color: ${({ theme }) => theme.color.spaceBackground};
  `,

  Row: styled.div`
    display: flex;
    height: calc(100% - ${HEADER_STYLE.height});
    gap: ${LAYOUT_STYLE.gap};
  `,

  LeftSidebarSection: styled.section<{ $isLeftSidebarOpen: boolean }>`
    ${sidebarStyle}
    display: ${({ $isLeftSidebarOpen }) => !$isLeftSidebarOpen && 'none'};
  `,

  Main: styled.main`
    display: flex;
    justify-content: center;
    width: 100%;
    box-shadow: ${LAYOUT_STYLE.boxShadow};
    border-radius: 8px;
    overflow-y: auto;
  `,

  RightSidebarSection: styled.section<{ $isRightSidebarOpen: boolean }>`
    ${sidebarStyle}
    display: ${({ $isRightSidebarOpen }) => !$isRightSidebarOpen && 'none'};
  `,
};
