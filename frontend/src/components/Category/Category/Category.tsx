import { DeleteIcon, PencilIcon } from 'assets/icons';
import { useEraseCategory } from 'components/Category/Category/useEraseCategory';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEvent, MouseEvent, useState } from 'react';
import { styled } from 'styled-components';
import { CategoryResponse } from 'types/apis/category';
import useCategoryInput from '../useCategoryInput';
import { usePatchCategory } from 'components/Category/Category/usePatchCategory';

const Category = ({ id, categoryName }: CategoryResponse) => {
  const [name, setName] = useState(categoryName);
  const {
    value,
    resetValue,
    inputRef,
    handleOnChange,
    escapeInput: escapeRename,
    isOpenInput,
    setIsOpenInput,
  } = useCategoryInput('');
  const { renameCategory } = usePatchCategory();
  const { eraseCategory } = useEraseCategory();
  const { goWritingTablePage } = usePageNavigate();

  const requestChangedName = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    renameCategory(id, value);
    setIsOpenInput(() => false);
    setName(() => value);
    resetValue();
  };

  const handlePencilIconClick = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    setIsOpenInput(true);
  };

  const handleTrashcanIconClick = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    eraseCategory(id);
  };

  return (
    <S.CategoryButton onClick={() => goWritingTablePage(id)}>
      {isOpenInput ? (
        <S.Input
          type='text'
          value={value}
          ref={inputRef}
          onChange={handleOnChange}
          onKeyDown={escapeRename}
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
