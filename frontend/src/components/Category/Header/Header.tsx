import { KeyboardEventHandler } from 'react';
import { styled } from 'styled-components';
import useUncontrolledInput from '../../../hooks/@common/useUncontrolledInput';
import { useCategoryMutation } from '../useCategoryMutation';
import Input from 'components/@common/Input/Input';
import { PlusIcon } from 'assets/icons';
import { useToast } from 'hooks/@common/useToast';
import { getErrorMessage } from 'utils/error';
import { validateCategoryName } from 'utils/validators';

const Header = () => {
  const {
    inputRef,
    escapeInput: escapeAddCategory,
    isInputOpen,
    openInput,
    resetInput,
    isError,
    setIsError,
  } = useUncontrolledInput();
  const { addCategory } = useCategoryMutation();
  const toast = useToast();

  const requestAddCategory: KeyboardEventHandler<HTMLInputElement> = async (e) => {
    try {
      if (e.key !== 'Enter') return;

      const categoryName = e.currentTarget.value.trim();

      validateCategoryName(categoryName);

      resetInput();
      addCategory({ categoryName: categoryName });
    } catch (error) {
      setIsError(true);
      toast.show({ type: 'error', message: getErrorMessage(error) });
    }
  };

  return (
    <S.Header>
      <S.Title>카테고리</S.Title>
      {isInputOpen ? (
        <Input
          type='text'
          variant='underlined'
          size='small'
          placeholder='추가할 카테고리'
          ref={inputRef}
          isError={isError}
          onBlur={resetInput}
          onKeyDown={escapeAddCategory}
          onKeyUp={requestAddCategory}
          aria-label='카테고리 추가 입력 창'
        />
      ) : (
        <S.Button onClick={openInput} aria-label='카테고리 추가 입력 창 열기'>
          <PlusIcon width={12} height={12} />
        </S.Button>
      )}
    </S.Header>
  );
};

export default Header;

const S = {
  Header: styled.header`
    position: sticky;
    top: 0;
    z-index: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 3.6rem;
    padding: 0.8rem;
    background-color: ${({ theme }) => theme.color.spaceBackground};
    font-size: 1.2rem;
    font-weight: 400;
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
    height: 2rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
};
