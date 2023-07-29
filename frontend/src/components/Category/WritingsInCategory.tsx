import { WritingIcon } from 'assets/icons';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { WritingInCategory } from 'types/apis/category';

type Props = {
  writings: WritingInCategory[];
};

const WritingsInCategory = ({ writings }: Props) => {
  // const navigate = useNavigate();

  const moveToWritingPage = (writingId: number) => {
    // TODO: 쿠마의 네비게이션 훅으로 교체 예정
    // navigate(`/writing/${writingId}`);
  };

  return (
    <S.List>
      {writings.map((writing) => (
        <S.Item>
          <S.Button onClick={() => moveToWritingPage(writing.id)}>
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

export default WritingsInCategory;

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
