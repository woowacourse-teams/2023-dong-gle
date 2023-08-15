import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goHomePage = () => navigate('/space');

  const goWritingPage = ({ categoryId, writingId }: { categoryId: number; writingId: number }) =>
    navigate(`/space/writings/${categoryId}/${writingId}`);

  const goWritingTablePage = (categoryId: number) => navigate(`/space/writings/${categoryId}`);

  return { goHomePage, goWritingPage, goWritingTablePage };
};
