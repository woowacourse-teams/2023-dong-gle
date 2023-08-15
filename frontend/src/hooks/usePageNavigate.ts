import { NAVIGATE_PATH } from 'constants/path';
import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goHomePage = () => navigate(NAVIGATE_PATH.homePage);

  const goWritingTablePage = (categoryId: number) =>
    navigate(NAVIGATE_PATH.getWritingTablePage(categoryId));

  const goWritingPage = ({ categoryId, writingId }: { categoryId: number; writingId: number }) =>
    navigate(NAVIGATE_PATH.getWritingPage(categoryId, writingId));

  return { goHomePage, goWritingTablePage, goWritingPage };
};
