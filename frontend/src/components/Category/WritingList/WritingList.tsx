import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { CategoryWriting } from 'types/components/category';

type Props = {
  writings: CategoryWriting[];
};

const WritingList = ({ writings }: Props) => {
  const { goWritingPage } = usePageNavigate();

  return (
    <ul>
      {writings.map((writing) => (
        <S.Item key={writing.id}>
          <S.Button onClick={() => goWritingPage(writing.id)}>
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

  Button: styled.button`
    display: flex;
    align-items: center;
    gap: 0.8rem;
    width: 100%;
    height: 3.6rem;
    padding: 0.8rem;
    border-radius: 8px;

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
};
