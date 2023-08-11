import { useNavigate } from 'react-router-dom';

export const usePageNavigate = () => {
  const navigate = useNavigate();

  const goWritingPage = ({ categoryId, writingId }: { categoryId: number; writingId: number }) =>
    navigate(`/writings/${categoryId}/${writingId}`);

  const goWritingTablePage = (categoryId: number) => navigate(`/writings/${categoryId}`);

  return { goWritingPage, goWritingTablePage };
};
