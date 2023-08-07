import { DeleteIcon, PencilIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEventHandler, MouseEventHandler, useState } from 'react';
import { styled } from 'styled-components';
import useCategoryInput from '../useCategoryInput';
import { useCategoryMutation } from '../useCategoryMutation';
import Input from 'components/@common/Input/Input';
import { isValidCategoryName } from '../isValidCategoryName';

type Props = {
  id: number;
  categoryName: string;
  isDefaultCategory: boolean;
  getCategories: () => Promise<unknown>;
};

const Category = ({ id, categoryName, isDefaultCategory, getCategories }: Props) => {
  const [name, setName] = useState(categoryName);
  const {
    value,
    inputRef,
    handleOnChange,
    escapeInput: escapeRename,
    isInputOpen,
    setIsInputOpen,
    resetInput,
    isError,
    setIsError,
  } = useCategoryInput('');
  const { patchCategory, deleteCategory } = useCategoryMutation();
  const { goWritingTablePage } = usePageNavigate();

  const requestChangedName: KeyboardEventHandler<HTMLInputElement> = async (e) => {
    if (e.key !== 'Enter') return;

    if (!isValidCategoryName(value)) {
      setIsError(true);
      return;
    }

    setName(value);
    resetInput();
    await patchCategory({
      categoryId: id,
      body: {
        categoryName: value.trim(),
      },
    });
    await getCategories();
  };

  const openRenamingInput: MouseEventHandler<SVGSVGElement> = (e) => {
    e.stopPropagation();

    setIsInputOpen(true);
  };

  const deleteCategoryClick: MouseEventHandler<SVGSVGElement> = async (e) => {
    e.stopPropagation();

    await deleteCategory(id);
    await getCategories();
  };

  return (
    <S.Container>
      {isInputOpen ? (
        <Input
          type='text'
          variant='underlined'
          size='small'
          placeholder='Change category name ...'
          value={value}
          ref={inputRef}
          isError={isError}
          onBlur={resetInput}
          onChange={handleOnChange}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
        />
      ) : (
        <>
          <S.CategoryButton
            onClick={() => goWritingTablePage(id)}
            aria-label={`${name} 카테고리 메인 화면에 열기`}
          >
            <S.Text>{name}</S.Text>
          </S.CategoryButton>
          {!isDefaultCategory && (
            <S.IconContainer>
              <S.Button aria-label={`${name} 카테고리 이름 수정`}>
                <PencilIcon onClick={openRenamingInput} width={12} height={12} />
              </S.Button>
              <S.Button aria-label={`${name} 카테고리 삭제`}>
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
