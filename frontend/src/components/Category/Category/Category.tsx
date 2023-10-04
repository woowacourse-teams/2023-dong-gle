import { PencilIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { KeyboardEventHandler } from 'react';
import { styled } from 'styled-components';
import useUncontrolledInput from '../../../hooks/@common/useUncontrolledInput';
import { useCategoryMutation } from '../useCategoryMutation';
import Input from 'components/@common/Input/Input';
import DeleteButton from 'components/DeleteButton/DeleteButton';
import { useToast } from 'hooks/@common/useToast';
import { getErrorMessage } from 'utils/error';
import { validateCategoryName } from 'utils/validators';

type Props = {
  categoryId: number;
  categoryName: string;
  isDefaultCategory: boolean;
};

const Category = ({ categoryId, categoryName, isDefaultCategory }: Props) => {
  const {
    inputRef,
    escapeInput: escapeRename,
    isInputOpen,
    openInput,
    resetInput,
    isError,
    setIsError,
  } = useUncontrolledInput();
  const { updateCategoryTitle, deleteCategory } = useCategoryMutation();
  const { goWritingTablePage } = usePageNavigate();
  const toast = useToast();

  const requestChangedName: KeyboardEventHandler<HTMLInputElement> = (e) => {
    try {
      if (e.key !== 'Enter') return;

      const categoryName = e.currentTarget.value.trim();

      validateCategoryName(categoryName);

      updateCategoryTitle({
        categoryId,
        body: {
          categoryName,
        },
      });

      resetInput();
    } catch (error) {
      setIsError(true);
      toast.show({ type: 'error', message: getErrorMessage(error) });
    }
  };

  return (
    <S.Container>
      {isInputOpen ? (
        <Input
          type='text'
          variant='underlined'
          size='small'
          placeholder='변경할 카테고리 이름'
          ref={inputRef}
          isError={isError}
          onBlur={resetInput}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
          aria-label={`${categoryName} 카테고리 이름 수정 입력 창`}
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
              <DeleteButton
                aria-label={`${categoryName} 카테고리 삭제`}
                onClick={() => deleteCategory(categoryId)}
              />
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
    width: 90%;
    height: 3.6rem;
    border-radius: 4px;
    font-size: 1.4rem;

    &:hover {
      div {
        display: inline-flex;
        gap: 0.4rem;
      }
    }
  `,

  CategoryButton: styled.button`
    flex: 1;
    min-width: 0;
    height: 100%;
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
    margin-right: 0.4rem;
  `,

  Button: styled.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    padding: 0.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
};
