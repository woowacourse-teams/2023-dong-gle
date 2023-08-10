import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { useCategoryWritings } from '../CategorySection/useCategoryWritings';
import { useEffect } from 'react';

type Props = {
  categoryId: number;
  selectedCategoryId: number | null;
};

const WritingList = ({ categoryId, selectedCategoryId }: Props) => {
  const { goWritingPage } = usePageNavigate();
  const { writings, getWritings } = useCategoryWritings();
  const writingId = Number(useParams()['writingId']);

  useEffect(() => {
    if (categoryId !== selectedCategoryId) return;

    getWritings(selectedCategoryId);
  }, []);

  if (!writings || writings?.length === 0)
    return <S.NoWritingsText>No Writings inside</S.NoWritingsText>;

  return (
    <ul>
      {writings.map((writing) => (
        <S.Item key={writing.id}>
          <S.Button
            $isClicked={writingId === writing.id}
            aria-label={`${writing.title}글 메인화면에 열기`}
            onClick={() => goWritingPage(writing.id)}
          >
            <S.IconWrapper>
              <WritingIcon width={14} height={14} />
            </S.IconWrapper>
            <S.Text>{writing.title}</S.Text>
          </S.Button>
        </S.Item>
      ))}
    </ul>
  );
};

export default WritingList;

const S = {
  Item: styled.li`
    width: 100%;
  `,

  Button: styled.button<{ $isClicked: boolean }>`
    display: flex;
    align-items: center;
    gap: 0.8rem;
    width: 100%;
    height: 3.6rem;
    padding: 0.8rem;
    border-radius: 8px;
    background-color: ${({ theme, $isClicked }) => $isClicked && theme.color.gray5};

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,

  IconWrapper: styled.div`
    flex-shrink: 0;
  `,

  Text: styled.p`
    color: ${({ theme }) => theme.color.gray9};
    font-size: 1.4rem;
    font-weight: 400;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  NoWritingsText: styled.p`
    padding: 0.8rem;
    color: ${({ theme }) => theme.color.gray6};
    font-size: 1.4rem;
    font-weight: 500;
    cursor: default;
  `,
};
