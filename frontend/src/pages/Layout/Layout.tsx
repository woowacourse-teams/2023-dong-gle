import { useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';
import { PlusCircleIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { useFileUpload } from 'hooks/useFileUpload';

import { LAYOUT_STYLE, sidebarStyle } from 'styles/layoutStyle';
import Header from 'components/Header/Header';

export type PageContextType = {
  isLeftSidebarOpen: boolean;
  isRightSidebarOpen: boolean;
};

const Layout = () => {
  const [isLeftSidebarOpen, setIsLeftSidebarOpen] = useState(false);
  const [isRightSidebarOpen, setIsRightSidebarOpen] = useState(false);
  const { openFinder } = useFileUpload('.md');

  const toggleLeftSidebar = () => {
    setIsLeftSidebarOpen(!isLeftSidebarOpen);
  };

  const toggleRightSidebar = () => {
    setIsRightSidebarOpen(!isRightSidebarOpen);
  };

  return (
    <S.Container>
      <Header toggleLeftSidebar={toggleLeftSidebar} toggleRightSidebar={toggleRightSidebar} />
      <S.Row>
        <S.SidebarSection isLeftSidebarOpen={isLeftSidebarOpen}>
          <Button
            size={'large'}
            icon={<PlusCircleIcon />}
            block={true}
            align='left'
            onClick={openFinder}
          >
            Add Post
          </Button>
        </S.SidebarSection>
        {/** 사이드바 컴포넌트 완성되면 대체 */}
        <S.Main>
          <Outlet context={{ isLeftSidebarOpen, isRightSidebarOpen } satisfies PageContextType} />
        </S.Main>
      </S.Row>
    </S.Container>
  );
};

export default Layout;

export const usePageContext = () => {
  return useOutletContext<PageContextType>();
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    padding: ${LAYOUT_STYLE.padding};
  `,

  Row: styled.div`
    flex: 1;
    display: flex;
    gap: ${LAYOUT_STYLE.gap};
  `,

  SidebarSection: styled.section<{ isLeftSidebarOpen: boolean }>`
    ${sidebarStyle}
    display: ${({ isLeftSidebarOpen }) => !isLeftSidebarOpen && 'none'};
  `,

  Main: styled.main`
    flex: 1;
  `,
};
