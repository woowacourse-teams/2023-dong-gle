import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';
import { PlusCircleIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { HEADER_STYLE, LAYOUT_STYLE, sidebarStyle } from 'styles/layoutStyle';
import Header from 'components/Header/Header';
import WritingSideBar from 'components/WritingSideBar/WritingSideBar';
import CategorySection from 'components/Category/Section/Section';
import { useModal } from 'hooks/@common/useModal';
import FileUploadModal from 'components/FileUploadModal/FileUploadModal';
import Divider from 'components/@common/Divider/Divider';
import TrashCan from 'components/TrashCan/TrashCan';

export type PageContext = {
  isLeftSidebarOpen?: boolean;
  isRightSidebarOpen?: boolean;
  setActiveWritingInfo?: Dispatch<SetStateAction<ActiveWritingInfo | null>>;
};

type ActiveWritingInfo = {
  id: number;
  isDeleted: boolean;
};

const Layout = () => {
  const [isLeftSidebarOpen, setIsLeftSidebarOpen] = useState(true);
  const [isRightSidebarOpen, setIsRightSidebarOpen] = useState(true);
  const [activeWritingInfo, setActiveWritingInfo] = useState<ActiveWritingInfo | null>(null);
  const { isOpen, openModal, closeModal } = useModal();
  const isWritingViewerActive = activeWritingInfo !== null;

  const toggleLeftSidebar = () => {
    setIsLeftSidebarOpen(!isLeftSidebarOpen);
  };

  const toggleRightSidebar = () => {
    setIsRightSidebarOpen(!isRightSidebarOpen);
  };

  return (
    <S.Container>
      <Header
        onClickLeftSidebar={toggleLeftSidebar}
        onClickRightSidebar={toggleRightSidebar}
        isWritingViewerActive={isWritingViewerActive}
      />
      <S.Row>
        <S.LeftSidebarSection $isLeftSidebarOpen={isLeftSidebarOpen}>
          <Button
            size={'large'}
            icon={<PlusCircleIcon width={22} height={22} />}
            block={true}
            align='left'
            onClick={openModal}
            aria-label='글 가져오기'
          >
            글 가져오기
          </Button>
          <FileUploadModal isOpen={isOpen} closeModal={closeModal} />
          <Divider />
          <CategorySection />
          <Divider />
          <TrashCan />
        </S.LeftSidebarSection>
        <S.Main>
          <Outlet
            context={
              {
                isLeftSidebarOpen,
                isRightSidebarOpen,
                setActiveWritingInfo,
              } satisfies PageContext
            }
          />
        </S.Main>
        {isWritingViewerActive && (
          <S.RightSidebarSection $isRightSidebarOpen={isRightSidebarOpen}>
            <WritingSideBar isPublishingSectionActive={!activeWritingInfo?.isDeleted} />
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
