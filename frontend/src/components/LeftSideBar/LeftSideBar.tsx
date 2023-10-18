import { HomeBorderIcon, PlusCircleIcon, TrashCanIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Divider from 'components/@common/Divider/Divider';
import FileUploadModal from 'components/FileUploadModal/FileUploadModal';
import GoToPageLink from 'components/GoToPageLink/GoToPageLink';
import CategorySection from 'components/Category/Section/Section';
import { NAVIGATE_PATH } from 'constants/path';
import { useModal } from 'hooks/@common/useModal';
import styled from 'styled-components';

const LeftSideBar = () => {
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <>
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
      <GoToPageLink pathname={NAVIGATE_PATH.spacePage}>
        <HomeBorderIcon />
        <S.GoToPageLinkText>전체 글</S.GoToPageLinkText>
      </GoToPageLink>
      <Divider />
      <CategorySection />
      <Divider />
      <GoToPageLink pathname={NAVIGATE_PATH.trashCanPage}>
        <TrashCanIcon width={20} height={20} />
        <S.GoToPageLinkText>휴지통</S.GoToPageLinkText>
      </GoToPageLink>
    </>
  );
};

export default LeftSideBar;

const S = {
  GoToPageLinkText: styled.p`
    font-size: 1.4rem;
    font-weight: 500;
  `,
};
