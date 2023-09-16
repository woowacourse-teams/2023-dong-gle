import { NAVIGATE_PATH } from 'constants/path';
import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goIntroducePage = () => navigate(NAVIGATE_PATH.introducePage);

  const goSpacePage = () => navigate(NAVIGATE_PATH.spacePage);

  const goWritingTablePage = (categoryId: number) => {
    navigate(NAVIGATE_PATH.getWritingTablePage(categoryId), {
      state: { categoryId },
    });
  };

  const goWritingPage = ({
    categoryId,
    writingId,
    isDeletedWriting = false,
  }: {
    categoryId: number;
    writingId: number;
    isDeletedWriting: boolean;
  }) => {
    navigate(NAVIGATE_PATH.getWritingPage(categoryId, writingId), {
      state: {
        categoryId,
        writingId,
        isDeletedWriting,
      },
    });
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
