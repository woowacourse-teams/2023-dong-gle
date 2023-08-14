import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goHomePage = () => navigate('/');

  const goWritingPage = ({ categoryId, writingId }: { categoryId: number; writingId: number }) =>
    navigate(`/writings/${categoryId}/${writingId}`);

  const goWritingTablePage = (categoryId: number) => navigate(`/writings/${categoryId}`);

  const goTrashCanPage = () => navigate('/trash-can');

  return { goHomePage, goWritingPage, goWritingTablePage, goTrashCanPage };
};
