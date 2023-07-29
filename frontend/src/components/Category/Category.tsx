import { DeleteIcon, PencilIcon } from 'assets/icons';
import { useCategoryName } from 'hooks/useCategoryName';
import { useEraseCategory } from 'hooks/useEraseCategory';
import { MouseEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { CategoryResponse } from 'types/apis/category';

const Category = ({ id, categoryName }: CategoryResponse) => {
  const {
    name,
    isRenaming,
    setIsRenaming,
    rename,
    inputRef,
    changeName,
    escapeChangeName,
    requestChangedName,
  } = useCategoryName(id, categoryName);
  const { eraseCategory } = useEraseCategory();

  // const navigate = useNavigate();
  const moveToWritingTablePage = () => {
    // TODO: 쿠마의 네비게이션 훅으로 교체 예정
    // navigate(`/writings/${id}`);
  };

  const handlePencilIconClick = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    setIsRenaming(!isRenaming);
  };

  const handleTrashcanIconClick = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    eraseCategory(id);
  };

  return (
    <S.CategoryButton onClick={moveToWritingTablePage}>
      {isRenaming ? (
        <S.Input
          type='text'
          value={rename}
          ref={inputRef}
          onChange={changeName}
          onKeyDown={escapeChangeName}
          onKeyUp={requestChangedName}
        />
      ) : (
        <S.Text>{name}</S.Text>
      )}
      <S.IconContainer>
        <PencilIcon onClick={handlePencilIconClick} width={12} height={12} />
        <DeleteIcon onClick={handleTrashcanIconClick} width={12} height={12} />
      </S.IconContainer>
    </S.CategoryButton>
  );
};

export default Category;

const S = {
  CategoryButton: styled.button`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 22.4rem;
    height: 3.2rem;
    padding: 0.8rem;
    border-radius: 8px;
    font-size: 1.4rem;

    &:hover {
      & > div {
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

  Input: styled.input``,

  IconContainer: styled.div`
    display: none;

    & > svg {
      cursor: pointer;
    }
  `,
};
