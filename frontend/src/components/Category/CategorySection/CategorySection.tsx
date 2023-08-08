import Accordion from 'components/@common/Accordion/Accordion';
import { styled } from 'styled-components';
import { useCategoryWritings } from 'components/Category/CategorySection/useCategoryWritings';
import { PlusCircleIcon } from 'assets/icons';
import { KeyboardEventHandler } from 'react';
import useCategoryInput from '../useCategoryInput';
import Category from '../Category/Category';
import WritingList from '../WritingList/WritingList';
import { useCategoryDetails } from './useCategoryDetails';
import { useCategoryMutation } from '../useCategoryMutation';
import Input from 'components/@common/Input/Input';
import { isValidCategoryName } from '../isValidCategoryName';

const CategorySection = () => {
  const { writings, selectedCategoryId, setSelectedCategoryId } = useCategoryWritings();
  const { categoryDetails } = useCategoryDetails(selectedCategoryId, writings);
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

    if (!isValidCategoryName(value)) {
      setIsError(true);
      return;
    }

    resetInput();
    addCategory({ categoryName: value.trim() });
  };

  if (!categoryDetails) return null;

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
      <Accordion>
        {categoryDetails.map((categoryDetail, index) => {
          return (
            <Accordion.Item key={categoryDetail.id}>
              <Accordion.Title
                onIconClick={() => setSelectedCategoryId(categoryDetail.id)}
                aria-label={`${categoryDetail.categoryName} 카테고리 왼쪽 사이드바에서 열기`}
              >
                <Category
                  categoryId={categoryDetail.id}
                  categoryName={categoryDetail.categoryName}
                  isDefaultCategory={index === 0}
                />
              </Accordion.Title>
              <Accordion.Panel>
                {categoryDetail.writings && <WritingList writings={categoryDetail.writings} />}
              </Accordion.Panel>
            </Accordion.Item>
          );
        })}
      </Accordion>
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
