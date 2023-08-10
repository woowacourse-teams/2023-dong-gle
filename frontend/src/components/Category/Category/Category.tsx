import { DeleteIcon, PencilIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEventHandler, MouseEventHandler, useState } from 'react';
import { styled } from 'styled-components';
import useCategoryInput from '../useCategoryInput';
import { useCategoryMutation } from '../useCategoryMutation';
import Input from 'components/@common/Input/Input';
import { isValidCategoryName } from '../isValidCategoryName';

type Props = {
  categoryId: number;
  categoryName: string;
  isDefaultCategory: boolean;
};

const Category = ({ categoryId, categoryName, isDefaultCategory }: Props) => {
  const {
    value,
    inputRef,
    handleOnChange,
    escapeInput: escapeRename,
    isInputOpen,
    openInput,
    resetInput,
    isError,
    setIsError,
  } = useCategoryInput('');
  const { patchCategory, deleteCategory } = useCategoryMutation();
  const { goWritingTablePage } = usePageNavigate();

  const requestChangedName: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key !== 'Enter') return;

    if (!isValidCategoryName(value)) {
      setIsError(true);
      return;
    }

    resetInput();
    patchCategory({
      categoryId,
      body: {
        categoryName: value.trim(),
      },
    });
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
            onClick={() => goWritingTablePage(categoryId)}
            aria-label={`${categoryName} 카테고리 메인 화면에 열기`}
          >
            <S.Text>{categoryName}</S.Text>
          </S.CategoryButton>
          {!isDefaultCategory && (
            <S.IconContainer>
              <S.Button aria-label={`${categoryName} 카테고리 이름 수정`} onClick={openInput}>
                <PencilIcon width={12} height={12} />
              </S.Button>
              <S.Button
                aria-label={`${categoryName} 카테고리 삭제`}
                onClick={() => deleteCategory(categoryId)}
              >
                <DeleteIcon width={12} height={12} />
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
