import { NAVIGATE_PATH } from 'constants/path';
import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goIntroducePage = () => navigate(NAVIGATE_PATH.introducePage);

  const goSpacePage = () => navigate(NAVIGATE_PATH.spacePage);

  const goWritingTablePage = (categoryId: number) =>
    navigate(NAVIGATE_PATH.getWritingTablePage(categoryId));

  const goWritingPage = ({ categoryId, writingId }: { categoryId: number; writingId: number }) =>
    navigate(NAVIGATE_PATH.getWritingPage(categoryId, writingId));

  const goTrashCanPage = () => navigate(NAVIGATE_PATH.trashCanPage);

  const goMyPage = () => navigate(NAVIGATE_PATH.myPage);

  return { goHomePage, goWritingTablePage, goWritingPage, goTrashCanPage, goMyPage };
};
