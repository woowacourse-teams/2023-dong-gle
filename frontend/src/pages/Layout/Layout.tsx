import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';
import { HomeBorderIcon, PlusCircleIcon, TrashCanIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { HEADER_STYLE, LAYOUT_STYLE, sidebarStyle } from 'styles/layoutStyle';
import Header from 'components/Header/Header';
import WritingSideBar from 'components/WritingSideBar/WritingSideBar';
import CategorySection from 'components/Category/Section/Section';
import { useModal } from 'hooks/@common/useModal';
import FileUploadModal from 'components/FileUploadModal/FileUploadModal';
import Divider from 'components/@common/Divider/Divider';
import HelpMenu from 'components/HelpMenu/HelpMenu';
import { useGlobalStateValue } from '@yogjin/react-global-state';
import { activeWritingInfoState } from 'globalState';
import { NAVIGATE_PATH } from 'constants/path';
import GoToPageLink from 'components/GoToPageLink/GoToPageLink';

const Layout = () => {
  const [isLeftSidebarOpen, setIsLeftSidebarOpen] = useState(true);
  const [isRightSidebarOpen, setIsRightSidebarOpen] = useState(true);
  const activeWritingInfo = useGlobalStateValue(activeWritingInfoState);
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
          <GoToPageLink path={NAVIGATE_PATH.spacePage}>
            <HomeBorderIcon aria-label='홈 아이콘' />
            <S.GoToPageLinkText>전체 글</S.GoToPageLinkText>
          </GoToPageLink>
          <Divider />
          <CategorySection />
          <Divider />
          <GoToPageLink path={NAVIGATE_PATH.trashCanPage}>
            <TrashCanIcon width={20} height={20} aria-label='휴지통 아이콘' />
            <S.GoToPageLinkText>휴지통</S.GoToPageLinkText>
          </GoToPageLink>
        </S.LeftSidebarSection>
        <S.Main>
          <Outlet />
          <HelpMenu />
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

  GoToPageLinkText: styled.p`
    font-size: 1.4rem;
    font-weight: 500;
  `,
};
