import Accordion from 'components/@common/Accordion/Accordion';
import { styled } from 'styled-components';
import { PlusCircleIcon } from 'assets/icons';
import { KeyboardEventHandler } from 'react';
import useCategoryInput from '../useCategoryInput';
import { useCategoryMutation } from '../useCategoryMutation';
import Input from 'components/@common/Input/Input';
import { isValidCategoryName } from '../isValidCategoryName';
import { useCategories } from './useCategories';
import CategoryItem from './CategoryItem';

const CategorySection = () => {
  const { categories } = useCategories();
  const {
    value,
    inputRef,
    handleOnChange,
    escapeInput: escapeAddCategory,
    isInputOpen,
    setIsInputOpen,
    resetInput,
    isError,
    setIsError,
  } = useCategoryInput('');
  const { addCategory } = useCategoryMutation();

  const requestAddCategory: KeyboardEventHandler<HTMLInputElement> = async (e) => {
    if (e.key !== 'Enter') return;

    const categoryName = value.trim();

    if (!isValidCategoryName(categoryName)) {
      setIsError(true);
      return;
    }

    resetInput();
    addCategory({ categoryName: categoryName });
  };

  return (
    <S.Section>
      <S.Header>
        <S.Title>카테고리</S.Title>
        {isInputOpen ? (
          <Input
            type='text'
            variant='underlined'
            size='small'
            placeholder='Add category ...'
            value={value}
            ref={inputRef}
            isError={isError}
            onBlur={resetInput}
            onChange={handleOnChange}
            onKeyDown={escapeAddCategory}
            onKeyUp={requestAddCategory}
          />
        ) : (
          <S.Button onClick={() => setIsInputOpen(true)} aria-label='카테고리 추가 입력 창 열기'>
            <PlusCircleIcon width={12} height={12} />
          </S.Button>
        )}
      </S.Header>
      {categories ? (
        <Accordion>
          {categories.map((category, index) => {
            return (
              <CategoryItem
                key={category.id}
                categoryId={category.id}
                categoryName={category.categoryName}
                isDefaultCategory={Boolean(index === 0)}
              />
            );
          })}
        </Accordion>
      ) : null}
    </S.Section>
  );
};

export default CategorySection;

const S = {
  Section: styled.section`
    width: 26rem;
    overflow: auto;
  `,

  Header: styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 2.8rem;
    font-size: 1.2rem;
    font-weight: 400;
    padding-right: 0.8rem;
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

  Title: styled.h1`
    color: ${({ theme }) => theme.color.gray8};
    cursor: default;
  `,

  Input: styled.input`
    border: none;
    outline: none;
    color: ${({ theme }) => theme.color.gray10};
    font-size: 1.2rem;
    font-weight: 600;

    &::placeholder {
      font-weight: 300;
    }
  `,
};
