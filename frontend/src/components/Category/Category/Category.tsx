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

    setName(value);
    closeInput();
    await patchCategory({
      categoryId: id,
      body: {
        categoryName: value,
      },
    });
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
    <S.Container>
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
          <S.CategoryButton onClick={() => goWritingTablePage(id)}>
            <S.Text>{name}</S.Text>
          </S.CategoryButton>
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
    border-radius: 8px;
    font-size: 1.4rem;

    &:hover {
      div {
        display: inline-flex;
        gap: 0.8rem;
      }
    }
  `,

  CategoryButton: styled.button`
    flex: 1;
    min-width: 0;
    text-align: left;
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
  `,
};
