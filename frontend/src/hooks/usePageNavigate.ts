import { useSetGlobalState } from '@yogjin/react-global-state-hook';
import { NAVIGATE_PATH } from 'constants/path';
import { activeCategoryIdState, activeWritingInfoState } from 'globalState';
import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();
  const setActiveCategoryId = useSetGlobalState(activeCategoryIdState);
  const setActiveWritingInfo = useSetGlobalState(activeWritingInfoState);

  const goIntroducePage = () => navigate(NAVIGATE_PATH.introducePage);

  const goSpacePage = () => navigate(NAVIGATE_PATH.spacePage);

  const goWritingTablePage = (categoryId: number) => {
    setActiveCategoryId(categoryId);
    navigate(NAVIGATE_PATH.getWritingTablePage(categoryId));
  };

  const goWritingPage = ({
    categoryId,
    writingId,
    isDeletedWriting = false,
  }: {
    categoryId: number;
    writingId: number;
    isDeletedWriting?: boolean;
  }) => {
    setActiveCategoryId(categoryId);
    setActiveWritingInfo({ id: writingId, isDeleted: isDeletedWriting });
    navigate(NAVIGATE_PATH.getWritingPage(categoryId, writingId));
  };
  const goTrashCanPage = () => navigate(NAVIGATE_PATH.trashCanPage);

  const goMyPage = () => navigate(NAVIGATE_PATH.myPage);

  return {
    goIntroducePage,
    goSpacePage,
    goWritingTablePage,
    goWritingPage,
    goTrashCanPage,
    goMyPage,
  };
};
