import { DeleteIcon, PencilIcon } from 'assets/icons';
import { styled } from 'styled-components';
import { CategoryResponse } from 'types/apis/category';

type Props = {
  numberOfWritings: number;
  onPencilClick?: () => void;
  onTrashcanClick: () => void;
} & CategoryResponse;

const Category = ({ categoryName, numberOfWritings, onPencilClick, onTrashcanClick }: Props) => {
  return (
    <S.Container>
      <S.Text>{categoryName}</S.Text>
      <S.RightContainer>
        <S.IconContainer>
          <PencilIcon onClick={onPencilClick} width={12} height={12} />
          <DeleteIcon onClick={onTrashcanClick} width={12} height={12} />
        </S.IconContainer>
        <S.NumberText>{numberOfWritings}</S.NumberText>
      </S.RightContainer>
    </S.Container>
  );
};

export default Category;

const S = {
  Container: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 22.4rem;
    height: 3.2rem;
    padding: 0.8rem;
    border-radius: 4px;
    font-size: 1.4rem;

    &:hover {
      & > div > div {
        display: flex;
        gap: 0.4rem;
      }
    }
  `,

  Text: styled.p`
    font-weight: 700;

    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  RightContainer: styled.div`
    display: flex;
    gap: 0.4rem;
  `,

  IconContainer: styled.div`
    display: none;

    & > svg {
      cursor: pointer;
    }
  `,

  NumberText: styled.p`
    font-weight: 300;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,
};
