import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { Writing } from 'types/apis/category';

type Props = {
  writingList: Writing[];
};

const WritingList = ({ writingList }: Props) => {
  const { goWritingPage } = usePageNavigate();

  return (
    <S.List>
      {writingList.map((writing) => (
        <S.Item key={writing.id}>
          <S.Button onClick={() => goWritingPage(writing.id)}>
            <S.IconWrapper>
              <WritingIcon width={14} height={14} />
            </S.IconWrapper>
            <S.Text>{writing.title}</S.Text>
          </S.Button>
        </S.Item>
      ))}
    </S.List>
  );
};

export default WritingList;

const S = {
  List: styled.ul`
    width: 22rem;
  `,

  Item: styled.li`
    width: 100%;
    height: 3.2rem;
    padding: 0.8rem;
    border-radius: 8px;
  `,

  Button: styled.button`
    display: flex;
    gap: 0.8rem;
    width: 100%;
  `,

  IconWrapper: styled.div`
    flex-shrink: 0;
  `,

  Text: styled.p`
    font-size: 1.4rem;
    font-weight: 500;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,
};
