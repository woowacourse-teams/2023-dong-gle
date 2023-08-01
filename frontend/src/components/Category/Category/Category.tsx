import { DeleteIcon, PencilIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEvent, MouseEvent, useState } from 'react';
import { styled } from 'styled-components';
import useCategoryInput from '../useCategoryInput';
import { useCategoryMutation } from '../useCategoryMutation';

type Props = {
  id: number;
  categoryName: string;
};

const Category = ({ id, categoryName }: Props) => {
  const [name, setName] = useState(categoryName);
  const {
    value,
    resetValue,
    inputRef,
    handleOnChange,
    escapeInput: escapeRename,
    isOpenInput,
    setIsOpenInput,
    closeInput,
  } = useCategoryInput('');
  const { patchCategory, deleteCategory } = useCategoryMutation();
  const { goWritingTablePage } = usePageNavigate();

  const requestChangedName = async (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    await patchCategory({
      categoryId: id,
      body: {
        categoryName: value,
      },
    });
    setIsOpenInput(false);
    setName(value);
    resetValue();
  };

  const openRenamingInput = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    setIsOpenInput(true);
  };

  const deleteCategoryClick = async (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    await deleteCategory(id);
  };

  return (
    <S.CategoryButton $isOpenInput={isOpenInput} onClick={() => goWritingTablePage(id)}>
      {isOpenInput ? (
        <S.Input
          type='text'
          value={value}
          ref={inputRef}
          onBlur={closeInput}
          onChange={handleOnChange}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
        />
      ) : (
        <>
          <S.Text>{name}</S.Text>
          <S.IconContainer>
            <button>
              <PencilIcon onClick={openRenamingInput} width={12} height={12} />
            </button>
            <button>
              <DeleteIcon onClick={deleteCategoryClick} width={12} height={12} />
            </button>
          </S.IconContainer>
        </>
      )}
    </S.CategoryButton>
  );
};

export default Category;

const S = {
  CategoryButton: styled.button<Record<'$isOpenInput', boolean>>`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 22.4rem;
    height: 3.2rem;
    padding: 0.8rem;
    border-radius: 8px;
    font-size: 1.4rem;

    cursor: ${({ $isOpenInput }) => ($isOpenInput ? 'default' : 'cursor')};

    &:hover {
      div {
        display: inline-flex;
        gap: 0.8rem;
      }
    }
  `,

  Text: styled.p`
    flex: 1;
    min-width: 0;
    text-align: left;

    font-weight: 700;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  Input: styled.input``,

  IconContainer: styled.div`
    display: none;
  `,
};
