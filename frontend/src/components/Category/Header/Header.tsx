import { KeyboardEventHandler } from 'react';
import { styled } from 'styled-components';
import useCategoryInput from '../useCategoryInput';
import { useCategoryMutation } from '../useCategoryMutation';
import { isValidCategoryName } from '../isValidCategoryName';
import Input from 'components/@common/Input/Input';
import { PlusCircleIcon } from 'assets/icons';

const Header = () => {
  const {
    inputRef,
    escapeInput: escapeAddCategory,
    isInputOpen,
    openInput,
    resetInput,
    isError,
    setIsError,
  } = useCategoryInput('');
  const { addCategory } = useCategoryMutation();

  const requestAddCategory: KeyboardEventHandler<HTMLInputElement> = async (e) => {
    if (e.key !== 'Enter') return;

    const categoryName = e.currentTarget.value.trim();

    if (!isValidCategoryName(categoryName)) {
      setIsError(true);
      return;
    }

    resetInput();
    addCategory({ categoryName: categoryName });
  };

  return (
    <S.Header>
      <S.Title>카테고리</S.Title>
      {isInputOpen ? (
        <Input
          type='text'
          variant='underlined'
          size='small'
          placeholder='Add category ...'
          ref={inputRef}
          isError={isError}
          onBlur={resetInput}
          onKeyDown={escapeAddCategory}
          onKeyUp={requestAddCategory}
        />
      ) : (
        <S.Button onClick={openInput} aria-label='카테고리 추가 입력 창 열기'>
          <PlusCircleIcon width={12} height={12} />
        </S.Button>
      )}
    </S.Header>
  );
};

export default Header;

const S = {
  Header: styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 2.8rem;
    font-size: 1.2rem;
    font-weight: 400;
    padding-right: 0.8rem;
  `,

  Title: styled.h1`
    color: ${({ theme }) => theme.color.gray8};
    cursor: default;
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
