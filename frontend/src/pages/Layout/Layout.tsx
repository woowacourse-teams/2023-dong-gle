import { Dispatch, MutableRefObject, RefObject, SetStateAction, useRef, useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';
import { PlusCircleIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { useFileUpload } from 'hooks/useFileUpload';

import { HEADER_STYLE, LAYOUT_STYLE, sidebarStyle } from 'styles/layoutStyle';
import Header from 'components/Header/Header';
import { usePageNavigate } from 'hooks/usePageNavigate';
import WritingSideBar from 'components/WritingSideBar/WritingSideBar';
import CategorySection from 'components/Category/CategorySection/CategorySection';

export type PageContext = {
  isLeftSidebarOpen?: boolean;
  isRightSidebarOpen?: boolean;
  setActiveWritingId?: Dispatch<SetStateAction<number | null>>;
};

const Layout = () => {
  const [isLeftSidebarOpen, setIsLeftSidebarOpen] = useState(true);
  const [isRightSidebarOpen, setIsRightSidebarOpen] = useState(true);
  const [activeWritingId, setActiveWritingId] = useState<number | null>(null);
  const { openFinder } = useFileUpload('.md');
  const isWritingViewerActive = activeWritingId !== null;

  const toggleLeftSidebar = () => {
    setIsLeftSidebarOpen(!isLeftSidebarOpen);
  };

  const toggleRightSidebar = () => {
    setIsRightSidebarOpen(!isRightSidebarOpen);
  };

  const { goWritingTablePage } = usePageNavigate();
  return (
    <S.Container>
      <Header
        onClickLeftSidebar={toggleLeftSidebar}
        onClickRightSidebar={toggleRightSidebar}
        isWritingViewerActive={isWritingViewerActive}
      />
      <S.Row>
        <S.LeftSidebarSection isLeftSidebarOpen={isLeftSidebarOpen}>
          <Button
            size={'large'}
            icon={<PlusCircleIcon />}
            block={true}
            align='left'
            onClick={openFinder}
          >
            Add Post
          </Button>
          <Button onClick={() => goWritingTablePage(1)}>ㅋㅋ</Button>
          <CategorySection />
        </S.LeftSidebarSection>
        <S.Main>
          <Outlet
            context={
              {
                isLeftSidebarOpen,
                isRightSidebarOpen,
                setActiveWritingId,
              } satisfies PageContext
            }
          />
        </S.Main>
        {isWritingViewerActive && (
          <S.RightSidebarSection isRightSidebarOpen={isRightSidebarOpen}>
            <WritingSideBar writingId={activeWritingId} />
          </S.RightSidebarSection>
        )}
      </S.Row>
    </S.Container>
  );
};

export default Layout;

export const usePageContext = () => {
  return useOutletContext<PageContext>();
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
    display: flex;
    height: calc(100% - ${HEADER_STYLE.height});
    gap: ${LAYOUT_STYLE.gap};
  `,

  LeftSidebarSection: styled.section<{ isLeftSidebarOpen: boolean }>`
    ${sidebarStyle}
    display: ${({ isLeftSidebarOpen }) => !isLeftSidebarOpen && 'none'};
  `,

  Main: styled.main`
    display: flex;
    justify-content: center;
    width: 100%;
    border: ${LAYOUT_STYLE.border};
    border-radius: 8px;
    overflow-y: auto;
  `,

  RightSidebarSection: styled.section<Pick<PageContext, 'isRightSidebarOpen'>>`
    ${sidebarStyle}
    display: ${({ isRightSidebarOpen }) => !isRightSidebarOpen && 'none'};
  `,
};
