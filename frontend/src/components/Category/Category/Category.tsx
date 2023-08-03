import { DeleteIcon, PencilIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEvent, MouseEvent, useState } from 'react';
import { styled } from 'styled-components';
import useCategoryInput from '../useCategoryInput';
import { useCategoryMutation } from '../useCategoryMutation';

type Props = {
  id: number;
  categoryName: string;
  getCategories: () => Promise<void>;
  isDefaultCategory: boolean;
};

const Category = ({ id, categoryName, getCategories, isDefaultCategory }: Props) => {
  const [name, setName] = useState(categoryName);
  const {
    value,
    inputRef,
    handleOnChange,
    escapeInput: escapeRename,
    isInputOpen,
    setIsInputOpen,
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
    await getCategories();
  };

  const openRenamingInput = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    setIsInputOpen(true);
  };

  const deleteCategoryClick = async (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    await deleteCategory(id);
    await getCategories();
  };

  return (
    <S.Container>
      {isInputOpen ? (
        <S.Input
          type='text'
          value={value}
          ref={inputRef}
          onBlur={closeInput}
          onChange={handleOnChange}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
          placeholder='Change category name'
        />
      ) : (
        <>
          <S.CategoryButton onClick={() => goWritingTablePage(id)}>
            <S.Text>{name}</S.Text>
          </S.CategoryButton>
          {!isDefaultCategory && (
            <S.IconContainer>
              <S.Button>
                <PencilIcon onClick={openRenamingInput} width={12} height={12} />
              </S.Button>
              <S.Button>
                <DeleteIcon onClick={deleteCategoryClick} width={12} height={12} />
              </S.Button>
            </S.IconContainer>
          )}
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
    width: 100%;
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
    color: ${({ theme }) => theme.color.gray10};
    font-weight: 600;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  Input: styled.input`
    border: none;
    outline: none;
    color: ${({ theme }) => theme.color.gray10};
    font-size: 1.3rem;
    font-weight: 600;

    &::placeholder {
      font-weight: 300;
    }
  `,

  IconContainer: styled.div`
    display: none;
  `,

  Button: styled.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    border-radius: 8px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
};
