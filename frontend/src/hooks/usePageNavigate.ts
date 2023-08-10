import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goHomePage = () => navigate('/');

  const goWritingPage = (writingId: number) => navigate(`/writing/${writingId}`);

  const goWritingTablePage = (categoryId: number) => navigate(`/writings/${categoryId}`);

  return { goHomePage, goWritingPage, goWritingTablePage };
};
