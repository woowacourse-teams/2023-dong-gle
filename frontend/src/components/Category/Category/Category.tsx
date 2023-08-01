import { DeleteIcon, PencilIcon } from 'assets/icons';
import { useEraseCategory } from 'components/Category/Category/useEraseCategory';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEvent, MouseEvent, useState } from 'react';
import { styled } from 'styled-components';
import useCategoryInput from '../useCategoryInput';
import { usePatchCategory } from 'components/Category/Category/usePatchCategory';

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
  } = useCategoryInput('');
  const { renameCategory } = usePatchCategory();
  const { eraseCategory } = useEraseCategory();
  const { goWritingTablePage } = usePageNavigate();

  const requestChangedName = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    renameCategory(id, value);
    setIsOpenInput(false);
    setName(value);
    resetValue();
  };

  const openRenamingInput = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    setIsOpenInput(true);
  };

  const deleteCategory = (e: MouseEvent<SVGSVGElement>) => {
    e.stopPropagation();

    eraseCategory(id);
  };

  return (
    <S.CategoryButton $isOpenInput={isOpenInput} onClick={() => goWritingTablePage(id)}>
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
        <>
          <S.Text>{name}</S.Text>
          <S.IconContainer>
            <button>
              <PencilIcon onClick={openRenamingInput} width={12} height={12} />
            </button>
            <button>
              <DeleteIcon onClick={deleteCategory} width={12} height={12} />
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
